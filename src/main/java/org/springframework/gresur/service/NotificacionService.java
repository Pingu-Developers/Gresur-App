package org.springframework.gresur.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.repository.NotificacionRepository;
import org.springframework.gresur.service.exceptions.NotificacionesLimitException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificacionService {

	private NotificacionRepository notificacionRepo;
	
	@Autowired
	private ConfiguracionService configuracionService;

	
	@Autowired
	public NotificacionService(NotificacionRepository notificacionRepo) {
		this.notificacionRepo = notificacionRepo;
	}
	
	@Transactional(readOnly = true)
	public List<Notificacion> findAll() throws DataAccessException{
		return notificacionRepo.findAll();
	}
	
	@Transactional(rollbackFor = {NotificacionesLimitException.class, NullPointerException.class})
	public Notificacion save(Notificacion notificacion) throws DataAccessException,NotificacionesLimitException,NullPointerException{
		
		if(notificacion.getLeido() == null)
			notificacion.setLeido(false);
		
		if(notificacion.getEmisor()==null && notificacion.getTipoNotificacion()!=TipoNotificacion.SISTEMA)
			throw new NullPointerException("El emisor no puede ser nulo si la notificación no es del sistema");
		
		notificacion.setFechaHora(LocalDateTime.now());
		Personal persona = notificacion.getEmisor();
		Integer maxNotificaciones = this.configuracionService.getNumMaxNotificaciones(); 
		
		Long n = persona.getNoti_enviadas().stream().filter(x->x.getFechaHora().isAfter(LocalDateTime.now().minusDays(1)))
				.filter(x->x.getTipoNotificacion().equals(TipoNotificacion.NORMAL)).count();
		
		if(n>maxNotificaciones) {
			throw new NotificacionesLimitException("Ha excedido el limite diario de notificaciones");
		}
		
		return notificacionRepo.save(notificacion);
	}
	
	@Transactional
	public List<Notificacion> findNoLeidasPersonal(Personal p){
		
		List<Notificacion> todas = notificacionRepo.findAll();
		
		return todas.stream().filter(x -> x.getReceptores().contains(p)).filter(x -> !x.getLeido()).collect(Collectors.toList());
	}
}
