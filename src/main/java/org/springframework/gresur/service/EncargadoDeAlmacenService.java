package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.repository.EncargadoDeAlmacenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EncargadoDeAlmacenService {
	
	private EncargadoDeAlmacenRepository encargadoDeAlmacenRepository;
	
	@Autowired
	public EncargadoDeAlmacenService(EncargadoDeAlmacenRepository encargadoDeAlmacenRepository) {
		this.encargadoDeAlmacenRepository = encargadoDeAlmacenRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<EncargadoDeAlmacen> findAll() throws DataAccessException{
		return encargadoDeAlmacenRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public EncargadoDeAlmacen findByNIF(String NIF) throws DataAccessException{
		return encargadoDeAlmacenRepository.findByNIF(NIF);
	}
	
	@Transactional
	public EncargadoDeAlmacen add(EncargadoDeAlmacen encargadoDeAlmacen) throws DataAccessException{
		return encargadoDeAlmacenRepository.save(encargadoDeAlmacen);
	}
	
	@Transactional
	public void deleteByNIF(String NIF) throws DataAccessException{
		encargadoDeAlmacenRepository.deleteByNIF(NIF);
	}
	

}
