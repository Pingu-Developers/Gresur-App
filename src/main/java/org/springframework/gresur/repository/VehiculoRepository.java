package org.springframework.gresur.repository;


import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.gresur.model.Vehiculo;

public interface VehiculoRepository extends PagingAndSortingRepository<Vehiculo, Long>{
	
	Optional<Vehiculo> findByMatricula(String matricula) throws DataAccessException;
	
	void deleteByMatricula(String matricula) throws DataAccessException;

}
