package org.springframework.gresur.repository;


import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Long>{
	Vehiculo findByMatricula(String matricula) throws DataAccessException;
	void deleteByMatricula(String matricula) throws DataAccessException;

}
