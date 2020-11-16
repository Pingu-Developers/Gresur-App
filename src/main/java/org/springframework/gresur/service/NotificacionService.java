package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Notificacion;
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
	
	@Transactional
	public Notificacion add(Notificacion notificacion) throws DataAccessException{
		return notificacionRepo.save(notificacion);
	}
}
