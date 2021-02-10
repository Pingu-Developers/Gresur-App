package org.springframework.gresur.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.LineaEnviado;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.model.userPayload.MessageResponse;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.LineasEnviadoService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/notificacion")
@RestController
@Slf4j
public class NotificacionController {

	private final NotificacionService notificacionService;
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LineasEnviadoService lineaEnviadoService;
	
	@Autowired
	AdministradorService admService;
	
	@Autowired
	public NotificacionController(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}
	
	@GetMapping("")
	@PreAuthorize("permitAll()")
	public List<Notificacion> notificacionesNoLeidas() {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		List<Notificacion> list = notificacionService.findNoLeidasPersonal(per);
		return list;
	}
	
	@GetMapping("/leidas")
	@PreAuthorize("permitAll()")
	public List<Notificacion> notificacionesLeidas() {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		List<Notificacion> list = notificacionService.findLeidasPersonal(per);
		return list;
	}
	
	@PostMapping("/setLeida/{id}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> setLeida(@PathVariable("id") Long id) {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		Notificacion noti = notificacionService.findById(id);
		
		if(noti==null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Notification not found!"));
		}
		
		else {
			
			LineaEnviado linea = noti.getLineasEnviado().stream().filter(x -> x.getPersonal().equals(per)).findFirst().orElseGet(null);
			
			if(linea != null) {
				linea.setLeido(true);
				lineaEnviadoService.save(linea);
				log.info("/notificacion/setLeida Entity LineaEnviadp with id: "+linea.getId()+" was edited successfully");
				return ResponseEntity.ok(noti);
			}
			else {
				return ResponseEntity.badRequest().body("Error: Notification not found!");
			}	
		}
	}

	@PostMapping()
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> nuevaNotificacion(@Valid @RequestBody Tuple3<List<Long>,String,String> noti) {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		try {
			Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
			
			List<Personal> receptores = noti.getE1().stream().map(x -> admService.findPersonal(x)).collect(Collectors.toList());
			
			Notificacion nuevaNoti = new Notificacion();
			
			nuevaNoti.setEmisor(per);
			nuevaNoti.setCuerpo(noti.getE2());
			nuevaNoti.setTipoNotificacion(TipoNotificacion.valueOf(noti.getE3()));
		
			Notificacion res = notificacionService.save(nuevaNoti, receptores);
			log.info("/notificacion/ Entity Notificacion with id: " + res.getId() +" was created successfully");
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			log.error("/notificacion/ "+e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
}
