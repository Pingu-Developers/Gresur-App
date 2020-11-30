package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.LineaEnviado;

public interface LineasEnviadoRepository extends CrudRepository<LineaEnviado, Long>{

	List<LineaEnviado> findAll();
	
}
