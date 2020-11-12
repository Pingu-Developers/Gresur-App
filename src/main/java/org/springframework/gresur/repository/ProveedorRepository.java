package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Proveedor;

public interface ProveedorRepository extends CrudRepository<Proveedor, Integer>{
	
	Proveedor findByNIF (String NIF) throws DataAccessException;
	
	void deleteByNIF(String NIF) throws DataAccessException;
}
