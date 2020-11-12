package org.springframework.gresur.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.repository.TransportistaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransportistaService {

	private TransportistaRepository transportistaRepo;
	
	@Autowired
	public TransportistaService(TransportistaRepository transportistaRepo) {
		this.transportistaRepo = transportistaRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Transportista> findAll() throws DataAccessException{
		return transportistaRepo.findAll();
	}	
	
	@Transactional(readOnly = true)
	public Transportista findByNIF(Integer NIF) throws DataAccessException {
		return transportistaRepo.findByNIF(NIF);
	}
	
	@Transactional
	public Transportista add(Transportista transportista) throws DataAccessException{
		return transportistaRepo.save(transportista);
	}
	
	@Transactional
	public void deleteByNIF(Integer NIF) {
		transportistaRepo.deleteByNIF(NIF);
	}

	
	
}
