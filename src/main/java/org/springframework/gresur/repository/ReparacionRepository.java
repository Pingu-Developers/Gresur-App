package org.springframework.gresur.repository;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Reparacion;

public interface ReparacionRepository extends CrudRepository<Reparacion, Long>{
	
	List<Reparacion> findAll();
	
	List<Reparacion> findByVehiculoMatricula(String matricula) throws DataAccessException;
	
	void deleteByVehiculoId(Long id);
	
	void deleteByVehiculoMatricula(String matricula);
	
	void deleteByRecibidasId(Long id);
	
}
