package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Dependiente;

public interface DependienteRepository extends CrudRepository<Dependiente, Integer>{

	Dependiente findByNIF(String NIF) throws DataAccessException;
	
	void deleteByNIF(String NIF) throws DataAccessException;
}
