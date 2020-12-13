package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorService {
	
	private ProveedorRepository proveedorRepo;
	
	@Autowired
	public ProveedorService(ProveedorRepository proveedorRepo) {
		this.proveedorRepo = proveedorRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Proveedor> findAll() throws DataAccessException{
		return proveedorRepo.findAll();
	}
	
	@Transactional
	public void deleteAll() {
		this.proveedorRepo.deleteAll();
	}
	
	@Transactional(readOnly = true)
	public Proveedor findByNIF(String NIF) throws DataAccessException{
		return proveedorRepo.findByNIF(NIF);
	}
	
	@Transactional
	public Proveedor save(Proveedor cliente) throws DataAccessException {
		return proveedorRepo.save(cliente);
	}
	
	@Transactional
	public void deleteByNIF(String NIF) throws DataAccessException{
		proveedorRepo.deleteByNIF(NIF);
	}
	
	@Transactional
	public Long count() throws DataAccessException{
		return proveedorRepo.count();
	}

}
