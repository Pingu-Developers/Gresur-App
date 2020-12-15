package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Estanteria;

public interface EstanteriaRepository extends CrudRepository<Estanteria, Long>{
	
	List<Estanteria> findAll();
	List<Estanteria> findByAlmacenId(Long id);
}
