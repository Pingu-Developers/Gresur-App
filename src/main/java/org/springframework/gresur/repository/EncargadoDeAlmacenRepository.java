package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.EncargadoDeAlmacen;

public interface EncargadoDeAlmacenRepository extends CrudRepository<EncargadoDeAlmacen, Integer> {
	
	EncargadoDeAlmacen findByNIF(String NIF) throws DataAccessException;

	void deleteByNIF(String NIF) throws DataAccessException;


	


}
