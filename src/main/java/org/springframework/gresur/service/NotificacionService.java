package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

	private NotificacionRepository notificacionRepo;
	
	@Autowired
	public NotificacionService(NotificacionRepository notificacionRepo) {
		this.notificacionRepo = notificacionRepo;
	}
	
}
