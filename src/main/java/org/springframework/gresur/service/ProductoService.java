package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.repository.ProductoRepository;
import org.springframework.gresur.service.exceptions.CapacidadProductoExcededException;
import org.springframework.gresur.service.exceptions.StockWithoutEstanteriaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {
	
	private ProductoRepository productoRepository;
	
	@Autowired
	private FacturaEmitidaService facturaService;
	
	
	@Autowired
	public ProductoService(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}
	
	/* AUXILIAR METHODS */
	
	private Double dimensionToDouble(String s) {
		
		Double x,y,z;
		
		String[] dimensiones = s.split("x");
		x = Double.parseDouble(dimensiones[0].trim());
		y = Double.parseDouble(dimensiones[1].trim());
		z = Double.parseDouble(dimensiones[2].trim());
		
		return x*y*z;
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
					.mapToDouble(x->dimensionToDouble(x.getDimensiones())*x.getStock()).sum() 
					+ dimensionToDouble(producto.getDimensiones())*producto.getStock();
			if(capacidadE < volumenProductos) {
				throw new CapacidadProductoExcededException("El volumen de los productos es mayor a la capacidad de la estanteria");
			}
		}
		else {
			if(producto.getStock()>0) 
				throw new StockWithoutEstanteriaException("No se puede añadir stock a un producto sin estanteria asociada");	
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
		List<LineaFactura> lf = facturaService.findAllLineasFactura().stream()
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
}
