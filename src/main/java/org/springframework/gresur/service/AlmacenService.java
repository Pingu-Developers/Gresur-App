package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.repository.AlmacenRepository;
import org.springframework.transaction.annotation.Transactional;

public class AlmacenService {
	
	private AlmacenRepository almacenRepository;
	
	@Autowired
	public AlmacenService(AlmacenRepository almacenRepository) {
		this.almacenRepository = almacenRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Almacen> findAll() throws DataAccessException{
		return almacenRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Almacen findById(Integer id) throws DataAccessException{
		return almacenRepository.findByid(id);
	}
	
	@Transactional
	public Almacen add(Almacen almacen) throws DataAccessException {
		return almacenRepository.save(almacen);
	}
	
	@Transactional
	public void deleteById(Integer id) throws DataAccessException{
		almacenRepository.deleteByid(id);
	} 
	

}
