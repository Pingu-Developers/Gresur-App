package org.springframework.gresur.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Seguro;

public interface SeguroRepository extends CrudRepository<Seguro, Long> {	
	
}
