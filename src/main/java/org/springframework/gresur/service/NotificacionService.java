package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificacionService {

	private NotificacionRepository notificacionRepo;
	
	@Autowired
	public NotificacionService(NotificacionRepository notificacionRepo) {
		this.notificacionRepo = notificacionRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Notificacion> findAll() throws DataAccessException{
		return notificacionRepo.findAll();
	}
	
	@Transactional(rollbackFor = NullPointerException.class)
	public Notificacion add(Notificacion notificacion) throws DataAccessException, NullPointerException{
		if(notificacion.getLeido() == null)
			notificacion.setLeido(false);
		if(notificacion.getEmisor()==null && notificacion.getTipoNotificacion()!=TipoNotificacion.SISTEMA)
			throw new NullPointerException("El emisor no puede ser nulo si la notificación no es del sistema");
		return notificacionRepo.save(notificacion);
	}
}
