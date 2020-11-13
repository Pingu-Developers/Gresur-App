package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Transportista;

public interface TransportistaRepository extends CrudRepository<Transportista, Integer>{

	 Transportista findByNIF(String NIF) throws DataAccessException;
	 void deleteByNIF(String NIF) throws DataAccessException;

}
