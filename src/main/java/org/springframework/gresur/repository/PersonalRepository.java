package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Personal;

public interface PersonalRepository<T extends Personal>  extends CrudRepository<T, Long>{
	
	T findByNIF(String NIF) throws DataAccessException;
	
	void deleteByNIF(String NIF) throws DataAccessException;
	
	List<T> findAll();
	
	Boolean existsByNIF(String NIF);
	
	Boolean existsByNSS(String NSS);
}
