package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Transportista;

public interface TransportistaRepository extends CrudRepository<Transportista, Integer>{

	 Transportista findByNIF(Integer NIF) throws DataAccessException;
	 void deleteByNIF(Integer NIF) throws DataAccessException;

}
