package org.springframework.gresur.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Factura;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.repository.FacturaRepository;
import org.springframework.transaction.annotation.Transactional;

public class FacturaService<T extends Factura, E extends FacturaRepository<T>> {
	
	protected E facturaRepo;
		
	@Autowired
	protected FacturaRepository<Factura> facturaGRepo;
	
	@Autowired
	protected LineasFacturaService lfService;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
	@Transactional(readOnly = true)
	public List<T> findAll() throws DataAccessException {
		return facturaRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public T findById(Long id) throws DataAccessException{
		return facturaRepo.findById(id).orElse(null);
	}
		
	@Transactional(rollbackFor = DataAccessException.class)
	public void deleteById(Long numFactura) throws DataAccessException{
		facturaRepo.deleteById(numFactura);
	}
	
	@Transactional
	public void deleteAll() throws DataAccessException{
		facturaRepo.deleteAll();
	}
	
	@Transactional(readOnly = true)
	public List<LineaFactura> findLineasFactura(){

		return facturaRepo.findAll().stream().map(x->x.getLineasFacturas())
			.filter(x->x!=null).flatMap(List::stream).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Long count() {
		return facturaRepo.count();
	}
	
	/*METODOS GENERALES PARA TODAS LAS FACTURAS (superclase)*/
	@Transactional(readOnly = true)
	public Iterable<Factura> findAllFacturas() throws DataAccessException{
		return facturaGRepo.findAll();
	}
		
	@Transactional(readOnly = true)
	public Long countAllFacturas() {
		return facturaGRepo.count();
	}
	
}
