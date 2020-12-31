package org.springframework.gresur.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/notificacion")
@RestController
public class NotificacionController {

	private final NotificacionService notificacionService;
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	public NotificacionController(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}
	
	@GetMapping("/number")
	public Integer countNotificationNoLeidas() {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		List<Notificacion> list = notificacionService.findNoLeidasPersonal(per);
		return list.size();
	}
}
