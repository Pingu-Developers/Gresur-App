package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.repository.AdministradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorService {
	
	private AdministradorRepository admRepository;
	
	@Autowired
	public AdministradorService(AdministradorRepository admRepository) {
		this.admRepository = admRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Administrador> findAll() throws DataAccessException{
		return admRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Administrador findByNIF(String NIF) throws DataAccessException{
		return admRepository.findByNIF(NIF);
	}
	
	@Transactional
	public Administrador add(Administrador adm) throws DataAccessException{
		return admRepository.save(adm);
	}
	
	@Transactional
	public void deleteByNIF(String NIF) throws DataAccessException{
		admRepository.deleteByNIF(NIF);
	}
	
}
