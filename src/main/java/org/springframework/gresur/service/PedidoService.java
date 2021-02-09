package org.springframework.gresur.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.PedidoRepository;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.gresur.service.exceptions.MMAExceededException;
import org.springframework.gresur.service.exceptions.PedidoConVehiculoSinTransportistaException;
import org.springframework.gresur.service.exceptions.PedidoLogisticException;
import org.springframework.gresur.service.exceptions.UnmodifablePedidoException;
import org.springframework.gresur.service.exceptions.VehiculoDimensionesExceededException;
import org.springframework.gresur.service.exceptions.VehiculoNotAvailableException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoService {
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ConfiguracionService configService;
	
	@Autowired
	private FacturaEmitidaService emitidaService;
	
	@Autowired
	private TransportistaService transportistaService;
	
	@Autowired
	private NotificacionService notiService;

	@PersistenceContext
	private EntityManager em;
	
	private PedidoRepository pedidoRepo;

	@Autowired
	public PedidoService(PedidoRepository pedidoRepo) {
		this.pedidoRepo = pedidoRepo;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


	@Transactional(readOnly = true)
	public Iterable<Pedido> findAll() throws DataAccessException {
		return pedidoRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Page<Pedido> findAll(Pageable pageable) throws DataAccessException {
		return pedidoRepo.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Pedido findByID(Long id) throws DataAccessException {
		return pedidoRepo.findById(id).orElseGet(null);
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> findByEstado(EstadoPedido estado) throws DataAccessException {
		return pedidoRepo.findByEstado(estado);
	}
	
	@Transactional(readOnly = true)
	public Page<Pedido> findByEstado(EstadoPedido estado,Pageable pageable) throws DataAccessException {
		return pedidoRepo.findByEstado(estado,pageable);
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> findAllByVehiculo(Long id) throws DataAccessException {
		return pedidoRepo.findByVehiculoId(id);
	}
	
	@Transactional
	public void deleteAll() {
		pedidoRepo.deleteAll();
	}
	

	@Transactional
	public Pedido save(Pedido pedido) throws DataAccessException {
		em.clear();
		
		Pedido ret;
		Vehiculo vehiculo = pedido.getVehiculo();
		LocalDate fecha = pedido.getFechaEnvio();
		
		if(pedido.getFechaRealizacion().isAfter(pedido.getFechaEnvio())) {
			throw new FechaFinNotAfterFechaInicioException("La fecha de realizacion debe ser anterior o igual a la fecha de envio");
		}
		
		Pedido anterior = pedido.getId() == null ? null : pedidoRepo.findById(pedido.getId()).orElse(null);
		if(anterior != null && anterior.getEstado().equals(EstadoPedido.EN_ESPERA) && (!pedido.getEstado().equals(EstadoPedido.EN_ESPERA) || !pedido.getEstado().equals(EstadoPedido.CANCELADO))) {
			FacturaEmitida fem = pedido.getFacturaEmitida();	
			fem.setNumFactura(configService.nextValEmitidas());
			fem.setFechaEmision(LocalDate.now());
			emitidaService.save(fem);
		}
		
		if(anterior != null && !anterior.equals(pedido) && (anterior.getEstado().equals(EstadoPedido.ENTREGADO) || anterior.getEstado().equals(EstadoPedido.CANCELADO))) {
			throw new UnmodifablePedidoException("El pedido ya ha sido entregado o cancelado y no puede modificarse");
		}	
		
		if(anterior != null && !anterior.getEstado().equals(EstadoPedido.EN_ESPERA) && (!pedido.getFacturaEmitida().equals(anterior.getFacturaEmitida())
																					|| !pedido.getDireccionEnvio().equals(anterior.getDireccionEnvio())
																					|| (!anterior.getEstado().equals(EstadoPedido.PREPARADO) && !pedido.getFechaEnvio().equals(anterior.getFechaEnvio())))) {
			throw new UnmodifablePedidoException("El pedido ya ha sido enviado y no puede modificarse");
		} else if(anterior != null && !anterior.getEstado().equals(EstadoPedido.EN_ESPERA) && pedido.getEstado().equals(EstadoPedido.CANCELADO)) {
			throw new UnmodifablePedidoException("El pedido ya ha sido enviado y no puede cancelarse"); 
		}
		
		if(vehiculo != null) {
			Double MMA = vehiculo.getMMA();
			if(pedido.getTransportista() == null) {
				throw new PedidoConVehiculoSinTransportistaException();
			} if(!vehiculoService.getDisponibilidad(vehiculo.getMatricula(), pedido.getTransportista())) {
				throw new VehiculoNotAvailableException();
			} if(pedido.getEstado().equals(EstadoPedido.EN_REPARTO) || pedido.getEstado().equals(EstadoPedido.ENTREGADO)) {
				
				List<Pedido> pedidos = pedidoRepo.findDistinctByVehiculoIdAndFechaEnvioAndEstadoIn(vehiculo.getId(), fecha, Arrays.asList(EstadoPedido.EN_REPARTO));
				
				List<List<LineaFactura>> lineasFactura= pedidos.stream()
						.map(x->x.getFacturaEmitida().getLineasFacturas())
						.collect(Collectors.toList());
						
				Double pesoTotal=0.;
				Double dimensionesTotal=0.;
				for (int i = 0; i < lineasFactura.size(); i++) {
					pesoTotal += lineasFactura.get(i).stream().mapToDouble(x->x.getCantidad()*x.getProducto().getPesoUnitario()).sum();
					dimensionesTotal += lineasFactura.get(i).stream().
							mapToDouble(x->x.getProducto().getAlto()*x.getProducto().getAncho()*x.getProducto().getProfundo()*x.getCantidad()).sum(); 
				}
				
				pesoTotal += pedido.getFacturaEmitida().getLineasFacturas().stream()
							.mapToDouble(x->x.getCantidad()*x.getProducto().getPesoUnitario())
							.sum();
				dimensionesTotal += pedido.getFacturaEmitida().getLineasFacturas().stream()
						.mapToDouble(x->x.getProducto().getAlto()*x.getProducto().getAncho()*x.getProducto().getProfundo()*x.getCantidad()).sum();
				
				if(pesoTotal>MMA) {
					throw new MMAExceededException();
				} 
				
				if(dimensionesTotal>vehiculo.getCapacidad()) {
					throw new VehiculoDimensionesExceededException();
				}
				
				else {
					ret =  pedidoRepo.save(pedido);
				}
			} else {
				throw new PedidoLogisticException();
			}
		} else {
			if(pedido.getTransportista() == null && (!pedido.getEstado().equals(EstadoPedido.EN_REPARTO) && !pedido.getEstado().equals(EstadoPedido.PREPARADO)) 
					|| pedido.getTransportista()!= null && pedido.getEstado().equals(EstadoPedido.PREPARADO)) {	
				ret = pedidoRepo.save(pedido);
			} else {
				throw new PedidoLogisticException();
			}
		}
		try {
			em.flush();
		} catch(Exception e) {
			log.warn("No se ha podido ejecutar flush");
		}
		return ret;
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> findPedidosByFecha(LocalDate fecha) {
		return pedidoRepo.findByFechaEnvio(fecha);
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> findPedidosEnRepartoByFecha(LocalDate fecha){
		return pedidoRepo.findByFechaEnvioAndEstadoIn(fecha, Arrays.asList(EstadoPedido.EN_REPARTO));
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> findPedidosEnRepartoByMatricula(String matricula){
		return pedidoRepo.findDistinctByVehiculoMatriculaAndEstadoIn(matricula, Arrays.asList(EstadoPedido.EN_REPARTO));
	}
	
	@Transactional(readOnly = true)
	public List<LineaFactura> findByProductoAndEstadoIn(EstadoPedido estado, Producto producto){
		return pedidoRepo.findByProductoAndEstadoIn(Arrays.asList(estado), producto);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException {
		pedidoRepo.deleteById(id);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Scheduled(cron = "0 0 7 * * *")
	@Transactional
	public void actualizaPedidos() {
		
		try {
			Queue<Pedido> candidatosQ = new LinkedList<Pedido> (pedidoRepo.findByEstadoAndFechaEnvioBeforeOrdered(EstadoPedido.EN_ESPERA.toString(), LocalDate.now()));
				
			while(candidatosQ.size() > 0) {
				Pedido p = candidatosQ.poll();
				
				// comprueba si hay stock para poder pasar a preparado
				if(p.getFacturaEmitida().getLineasFacturas().stream().allMatch(x -> x.getCantidad() <= productoService.findById(x.getProducto().getId()).getStock())) {
					
					// actualiza el stock de los producto
					p.getFacturaEmitida().getLineasFacturas().forEach(lf -> {
						Producto producto = productoService.findById(lf.getProducto().getId());
						producto.setStock(producto.getStock()-lf.getCantidad());
						productoService.save(producto);
					});
					// actualiza el pedido y emite la factura
					if(p.recogeEnTienda()) {
						p.setEstado(EstadoPedido.EN_TIENDA);
					} else {
						p.setEstado(EstadoPedido.PREPARADO);
						p.setTransportista(transportistaService.findTransportistaConMenosPedidos());
					}
					this.save(p);
					
					// manda notificacion
					FacturaEmitida fem = p.getFacturaEmitida();
					Notificacion noti = new Notificacion();
					if (p.recogeEnTienda()) {
						noti.setCuerpo("El pedido con id " + p.getId() + " se encuentra disponible en tienda, avise al cliente " + fem.getCliente().getName() + " con NIF: " + fem.getCliente().getNIF());
					} else {
						noti.setCuerpo("El pedido con id " + p.getId() + " ha sido tramitado y pasa a disposicion del transportista " + p.getTransportista().getName() + " para el transporte");
					}
					noti.setEmisor(null);
					noti.setTipoNotificacion(TipoNotificacion.SISTEMA);
					noti.setFechaHora(LocalDateTime.now());
					List<Personal> receptores = new ArrayList<Personal>();
					receptores.add(fem.getDependiente());
					notiService.save(noti, receptores);
				}
			}
			log.info("Pedidos actualizados");
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}
}