package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Administrador;

public interface AdministradorRepository extends CrudRepository<Administrador, Integer>{
	
	Administrador findByNIF(String NIF) throws DataAccessException;
	
	void deleteByNIF(String NIF) throws DataAccessException;
}
