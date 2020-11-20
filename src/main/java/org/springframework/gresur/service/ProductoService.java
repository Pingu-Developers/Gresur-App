package org.springframework.gresur.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.repository.FacturaEmitidaRepository;
import org.springframework.gresur.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {
	
	private ProductoRepository productoRepository;
	private FacturaEmitidaRepository facturaRepository;
	
	/* CRUD METHODS */
	
	@Autowired
	public ProductoService(ProductoRepository productoRepository, FacturaEmitidaRepository facturaRepository) {
		this.productoRepository = productoRepository;
		this.facturaRepository = facturaRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Producto> findAll() throws DataAccessException{
		return productoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Producto findById(Long id) throws DataAccessException{
		return productoRepository.findById(id).get();
	}
	
	@Transactional
	public Producto add(Producto producto) throws DataAccessException {
		return productoRepository.save(producto);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		productoRepository.deleteById(id);
	}
	
	/* USER STORIES */
	
	@Transactional(readOnly = true)
	public Double getDemanda(Producto p) throws DataAccessException{
		List<LineaFactura> lf = facturaRepository.findAllLineasFactura();
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
