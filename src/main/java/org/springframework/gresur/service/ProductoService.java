package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.repository.PedidoRepository;
import org.springframework.gresur.repository.ProductoRepository;
import org.springframework.gresur.service.exceptions.CapacidadProductoExcededException;
import org.springframework.gresur.service.exceptions.StockWithoutEstanteriaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {
	
	private ProductoRepository productoRepository;
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private AdministradorService adminService;
	
	@Autowired
	private FacturaEmitidaService facturaService;
	
	
	@Autowired
	public ProductoService(ProductoRepository productoRepository, PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.productoRepository = productoRepository;
	}
	
	/* CRUD METHODS */
	
	@Transactional(readOnly = true)
	public Iterable<Producto> findAll() throws DataAccessException{
		return productoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Producto findById(Long id) throws DataAccessException{
		return productoRepository.findById(id).get();
	}

	@Transactional(rollbackFor = {CapacidadProductoExcededException.class,StockWithoutEstanteriaException.class})
	public Producto save(Producto producto) throws DataAccessException,CapacidadProductoExcededException, StockWithoutEstanteriaException {
		Estanteria estanteria = producto.getEstanteria();
		if(estanteria != null) {
			Double capacidadE = estanteria.getCapacidad();
			Double volumenProductos = estanteria.getProductos().stream().filter(x->!x.getId().equals(producto.getId()))
					.mapToDouble(x->x.getAlto()*x.getAncho()*x.getProfundo()*x.getStock()).sum()
					+ producto.getAlto()*producto.getAncho()*producto.getProfundo()*producto.getStock();
			if(capacidadE < volumenProductos) {
				throw new CapacidadProductoExcededException("El volumen de los productos es mayor a la capacidad de la estanteria");
			}
		}
		else {
			if(producto.getStock()>0) 
				throw new StockWithoutEstanteriaException("No se puede añadir stock a un producto sin estanteria asociada");	
	}
		
		if(producto.getStock() <= producto.getStockSeguridad()) {
			Notificacion noti = new Notificacion();
			noti.setTipoNotificacion(TipoNotificacion.SISTEMA);
			noti.setCuerpo("El producto: '("+producto.getId()+")-"+producto.getNombre()+"' esta a punto de agotarse, considere reponerlo");
			
			List<Personal> lAdm = new ArrayList<>();
			for (Administrador adm : adminService.findAll()) {
				lAdm.add(adm);
			}
			notificacionService.save(noti,lAdm);
		}
		
		return productoRepository.save(producto);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		productoRepository.deleteById(id);
	}
	
	/* USER STORIES */
	
	@Transactional(readOnly = true)
	public Double getDemanda(Producto p, LocalDate fromDate) throws DataAccessException{
		
		if(fromDate == null) {
			fromDate = LocalDate.now().minusMonths(1);
		}
		LocalDate tmp = fromDate;	
		List<LineaFactura> lf = facturaService.findLineasFactura().stream()
																	 .filter(x->x.getFactura().getFecha().isAfter(tmp))
																	 .collect(Collectors.toList());
		Long totalVentas = lf.stream()
							 .mapToLong(x->x.getCantidad())
							 .sum();
		Double ventasProducto = lf.stream()
								  .filter(x->x.getProducto().equals(p))
								  .mapToDouble(x->x.getCantidad())
								  .sum();
		return ventasProducto/totalVentas;
	}
	
	public Integer stockRequerido(Producto p) {
		List<LineaFactura> lf = this.pedidoRepository.findByProductoAndEstadoIn(Arrays.asList(EstadoPedido.EN_ESPERA), p);
		Integer stockDemandado = lf.stream().mapToInt(x->x.getCantidad()).sum();
		return p.getStock() - stockDemandado;
	}
	
	@Transactional(readOnly = true)
	public List<Producto> findAllProductosByName(String s){
		return productoRepository.findByNombreContainingIgnoreCase(s.trim());
	}
	@Transactional(readOnly = true)
	public List<Producto> findByEstanteria(Categoria c){
		return productoRepository.findByEstanteriaCategoria(c);
	}

}
