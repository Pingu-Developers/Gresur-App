package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Notificacion;

public interface NotificacionRepository extends CrudRepository<Notificacion, Long>{
	
	List<Notificacion> findAll();
}
