package org.springframework.gresur.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.TipoNotificacion;

public interface NotificacionRepository extends CrudRepository<Notificacion, Long>{
	
	List<Notificacion> findAll();
	
	List<Notificacion> findByEmisorId(Long id);
	
	List<Notificacion> findByEmisorIdAndFechaHoraAfterAndTipoNotificacionIn(Long id, LocalDateTime fecha, Collection<TipoNotificacion> tipos);
	
	List<Notificacion> findAllNotificacionesByEmisorName(String name);
	
	@Query("SELECT noti FROM Notificacion noti INNER JOIN noti.lineasEnviado l WHERE l.personal.id = :id and l.leido = false")
	List<Notificacion> findNoLeidasForPersonal(@Param("id") Long id);
	
	@Query("SELECT noti FROM Notificacion noti INNER JOIN noti.lineasEnviado l WHERE l.personal.id = :id and l.leido = true ORDER BY noti.fechaHora DESC")
	List<Notificacion> findLeidasForPersonal(@Param("id") Long id);
}
