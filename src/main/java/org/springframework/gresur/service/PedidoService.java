package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.PedidoRepository;
import org.springframework.gresur.service.exceptions.MMAExceededException;
import org.springframework.gresur.service.exceptions.PedidoLogisticException;
import org.springframework.gresur.service.exceptions.PedidoNoDeleteableException;
import org.springframework.gresur.service.exceptions.PedidoSinTransportistaException;
import org.springframework.gresur.service.exceptions.VehiculoNotAvailableException;
import org.springframework.gresur.service.exceptions.VehiculoDimensionesExceededException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	private PedidoRepository pedidoRepo;

	@Autowired
	public PedidoService(PedidoRepository pedidoRepo) {
		this.pedidoRepo = pedidoRepo;
	}

	@Transactional(readOnly = true)
	public Iterable<Pedido> findAll() throws DataAccessException {
		return pedidoRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Pedido findByID(Long id) throws DataAccessException {
		return pedidoRepo.findById(id).get();
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
		
		Vehiculo vehiculo = pedido.getVehiculo();
		Double MMA = vehiculo.getMMA();
		LocalDate fecha = pedido.getFechaEnvio();
		
		if(pedido.getVehiculo() != null) {
			if(pedido.getTransportista() == null) {
				throw new PedidoSinTransportistaException();
			} if(!vehiculo.getDisponibilidad()) {
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
					return pedidoRepo.save(pedido);
				}
			} else {
				throw new PedidoLogisticException();
			}
		} else {
			if(pedido.getTransportista() == null && pedido.getEstado().equals(EstadoPedido.EN_ESPERA) || pedido.getTransportista()!= null && pedido.getEstado().equals(EstadoPedido.PREPARADO)) {
				return pedidoRepo.save(pedido);
			} else {
				throw new PedidoLogisticException();
			}
		}
		
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> findPedidosByFecha(LocalDate fecha) {
		return pedidoRepo.findByFechaEnvio(fecha);
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> findPedidosEnRepartoByFecha(LocalDate fecha){
		return pedidoRepo.findByFechaEnvioAndEstadoIn(fecha, Arrays.asList(EstadoPedido.EN_REPARTO));
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException {
		Pedido p = pedidoRepo.findById(id).orElse(null);
		
		if(p.getEstado().equals(EstadoPedido.EN_ESPERA)) {
			pedidoRepo.deleteById(id);
		}
		else {
			throw new PedidoNoDeleteableException();
		}
	}

}