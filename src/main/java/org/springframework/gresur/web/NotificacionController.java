package org.springframework.gresur.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.LineaEnviado;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.userPayload.JwtResponse;
import org.springframework.gresur.model.userPayload.MessageResponse;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.LineasEnviadoService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	LineasEnviadoService lineaEnviadoService;
	
	@Autowired
	public NotificacionController(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}
	
	
	@GetMapping("")
	public List<Notificacion> notificacionesNoLeidas() {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		List<Notificacion> list = notificacionService.findNoLeidasPersonal(per);
		return list;
	}
	
	@PostMapping("/setLeida/{id}")
	public ResponseEntity<?> setLeida(@PathVariable("id") Long id) {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		Notificacion noti = notificacionService.findById(id);
		
		LineaEnviado linea = noti.getLineasEnviado().stream().filter(x -> x.getPersonal().equals(per)).findFirst().orElseGet(null);
		
		
		if(linea != null) {
			
			linea.setLeido(true);
			lineaEnviadoService.save(linea);
			return ResponseEntity.ok(noti);
		}
		else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Notification not found!"));
		}
	}
}
