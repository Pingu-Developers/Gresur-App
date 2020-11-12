package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.repository.DependienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DependienteService {

	private DependienteRepository dependienteRepository;
	
	@Autowired
	public DependienteService(DependienteRepository dependienteRepository) {
		this.dependienteRepository= dependienteRepository;
	}
	
	
	@Transactional(readOnly = true)
	public Iterable<Dependiente> findAll() throws DataAccessException{
		return dependienteRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Dependiente findByNIF(String NIF) throws DataAccessException{
		return dependienteRepository.findByNIF(NIF);
	}
	
	@Transactional
	public Dependiente add(Dependiente dependiente) throws DataAccessException{
		return dependienteRepository.save(dependiente);
	}
	
	@Transactional(readOnly = true)
	public void deleteByNIF(String NIF) throws DataAccessException{
		dependienteRepository.deleteByNIF(NIF);
	}
	
}
