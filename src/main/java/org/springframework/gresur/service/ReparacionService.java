package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.repository.ReparacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReparacionService {

	private ReparacionRepository reparacionRepo;
	
	@Autowired
	public ReparacionService(ReparacionRepository reparacionRepo) {
		this.reparacionRepo = reparacionRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Reparacion> findAll() throws DataAccessException{
		return reparacionRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Reparacion findById(Long id) throws DataAccessException{
		return reparacionRepo.findById(id).orElse(null);
	}
	
	@Transactional
	public Reparacion add(Reparacion reparacion) throws DataAccessException{
		return reparacionRepo.save(reparacion);
	}
	
}
