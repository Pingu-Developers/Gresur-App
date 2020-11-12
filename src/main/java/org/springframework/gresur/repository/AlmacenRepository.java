package org.springframework.gresur.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Almacen;

public interface AlmacenRepository extends CrudRepository<Almacen, Integer>{
	
	Almacen findByid(Integer id) throws DataAccessException;

	void deleteByid(Integer id);
	
}
