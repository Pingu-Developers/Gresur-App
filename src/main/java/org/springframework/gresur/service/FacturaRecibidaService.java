package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.repository.FacturaRecibidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaRecibidaService {

	private FacturaRecibidaRepository facturaRecibidaRepository;

	@Autowired
	public FacturaRecibidaService(FacturaRecibidaRepository facturaRecibidarepository) {
		this.facturaRecibidaRepository = facturaRecibidarepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<FacturaRecibida> findAll() throws DataAccessException {
		return facturaRecibidaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public FacturaRecibida findByNumFactura(Integer numFactura) throws DataAccessException{
		return facturaRecibidaRepository.findById(numFactura).get();
	}
	
	@Transactional
	public FacturaRecibida add(FacturaRecibida facturaRecibida) throws DataAccessException {
		return facturaRecibidaRepository.save(facturaRecibida);
	}
	
	@Transactional
	public void deleteByNumFactura(Integer numFactura) throws DataAccessException{
		facturaRecibidaRepository.deleteById(numFactura);
	} 
}