package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.ITV;

public interface ITVRepository extends CrudRepository<ITV, Long>{
	
	List<ITV> findByVehiculo(Long id);

}
