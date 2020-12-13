package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.repository.AlmacenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
	
	@Transactional
	public void deleteAll() {
		this.almacenRepository.deleteAll();
	}
	
	@Transactional(readOnly = true)
	public Almacen findById(Long id) throws DataAccessException{
		return almacenRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Almacen save(Almacen almacen) throws DataAccessException {
		return almacenRepository.save(almacen);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		almacenRepository.deleteById(id);
	} 
	
	@Transactional
	public long count() throws DataAccessException{
		return almacenRepository.count();
	} 
	
	@Transactional
	public void deletAll() {
		almacenRepository.deleteAll();
	}
	

}
