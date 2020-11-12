package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;

public class ProductoService {
	
	private ProductoRepository productoRepository;
	
	@Autowired
	public ProductoService(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Producto> findAll() throws DataAccessException{
		return productoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Producto findById(Integer id) throws DataAccessException{
		return productoRepository.findByid(id);
	}
	
	@Transactional
	public Producto add(Producto producto) throws DataAccessException {
		return productoRepository.save(producto);
	}
	
	@Transactional
	public void deleteById(Producto id) throws DataAccessException{
		productoRepository.deleteByid(id);
	} 
}
