package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificacionController {

	private final NotificacionService notificacionService;
	
	@Autowired
	public NotificacionController(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}
}
