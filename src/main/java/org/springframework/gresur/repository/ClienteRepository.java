package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Integer> {

	Cliente findByNIF (String NIF) throws DataAccessException;
	
	void deleteByNIF(String NIF) throws DataAccessException;
}
