package org.springframework.gresur.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.repository.FacturaEmitidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaEmitidaService {

	private FacturaEmitidaRepository facturaEmitidaRepository;
	
	@Autowired
	public FacturaEmitidaService(FacturaEmitidaRepository facturaEmitidaRepository) {
		this.facturaEmitidaRepository= facturaEmitidaRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<FacturaEmitida> findAll() throws DataAccessException {
		return facturaEmitidaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public FacturaEmitida findByNumFactura(Integer numFactura) throws DataAccessException{
		return facturaEmitidaRepository.findById(numFactura).get();
	}
	
	@Transactional
	public FacturaEmitida add(FacturaEmitida facturaEmitida) throws DataAccessException {
		return facturaEmitidaRepository.save(facturaEmitida);
	}
	
	@Transactional
	public void deleteByNumFactura(Integer numFactura) throws DataAccessException{
		facturaEmitidaRepository.deleteById(numFactura);
	} 
}