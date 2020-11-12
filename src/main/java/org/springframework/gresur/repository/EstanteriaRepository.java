package org.springframework.gresur.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Estanteria;

public interface EstanteriaRepository extends CrudRepository<Estanteria, Integer>{

	void deleteByid(Integer id);

	Estanteria findByid(Integer id);
	
	

}
