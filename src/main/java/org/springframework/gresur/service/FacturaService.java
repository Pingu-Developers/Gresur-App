package org.springframework.gresur.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Factura;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.repository.FacturaRepository;
import org.springframework.gresur.service.exceptions.ClienteDefaulterException;
import org.springframework.transaction.annotation.Transactional;

public class FacturaService<T extends Factura, E extends FacturaRepository<T>> {
	
	protected E facturaRepo;
	
	@Autowired
	protected FacturaRepository<Factura> facturaGRepo;
	
	@Transactional(readOnly = true)
	public Iterable<T> findAll() throws DataAccessException {
		return facturaRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public T findByNumFactura(Long numFactura) throws DataAccessException{
		return facturaRepo.findById(numFactura).get();
	}
	
	@Transactional
	public T save(T facturaRecibida) throws DataAccessException, ClienteDefaulterException {
		return facturaRepo.save(facturaRecibida);
	}
	
	@Transactional
	public void deleteByNumFactura(Long numFactura) throws DataAccessException{
		facturaRepo.deleteById(numFactura);
	}
	
	@Transactional(readOnly = true)
	public List<LineaFactura> findLineasFactura(){
		return facturaRepo.findAll().stream().map(x->x.getLineasFacturas()).flatMap(List::stream).collect(Collectors.toList());
	}
	
	/*METODOS GENERALES PARA TODAS LAS FACTURAS (superclase)*/
	@Transactional(readOnly = true)
	public Iterable<Factura> findAllFacturas() throws DataAccessException{
		return facturaGRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Factura findFactura(Long numFactura) {
		return facturaGRepo.findById(numFactura).get();
	}
	
}
