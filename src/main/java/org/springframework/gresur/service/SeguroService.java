package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.repository.SeguroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguroService {
	
	private SeguroRepository seguroRepo;
	
	@Autowired
	public SeguroService(SeguroRepository seguroRepo) {
		this.seguroRepo = seguroRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Seguro> findAll() throws DataAccessException{
		return seguroRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Seguro findById(Long id) throws DataAccessException{
		return seguroRepo.findById(id).orElse(null);
	}
	
	@Transactional
	public Seguro save(Seguro seguro) throws DataAccessException{
		return seguroRepo.save(seguro);
	}

}
