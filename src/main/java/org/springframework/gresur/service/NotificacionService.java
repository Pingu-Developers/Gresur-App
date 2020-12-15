package org.springframework.gresur.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.LineaEnviado;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.repository.NotificacionRepository;
import org.springframework.gresur.service.exceptions.NotificacionesLimitException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificacionService {

	@PersistenceContext
	private EntityManager em;
	
	private NotificacionRepository notificacionRepo;
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	@Autowired
	private LineasEnviadoService lineaEnviadoService;

	
	@Autowired
	public NotificacionService(NotificacionRepository notificacionRepo) {
		this.notificacionRepo = notificacionRepo;
	}
	
	@Transactional(readOnly = true)
	public Notificacion findById(Long id){
		return this.notificacionRepo.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public List<Notificacion> findAll() throws DataAccessException{
		return notificacionRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Notificacion> findAllNotificacionesByEmisor(Long id) throws DataAccessException{
		return notificacionRepo.findByEmisorId(id);
	}
	
	@Transactional(readOnly = true)
	public List<Notificacion> findAllNotificacionesByEmisorName(String name)throws DataAccessException{
		return notificacionRepo.findAllNotificacionesByEmisorName(name);
	}

	@Transactional
	public Notificacion save(Notificacion notificacion,List<Personal> receptores) throws DataAccessException{
		
		if(notificacion.getEmisor()==null && notificacion.getTipoNotificacion()!=TipoNotificacion.SISTEMA)
			throw new NullPointerException("El emisor no puede ser nulo si la notificación no es del sistema");
		
		notificacion.setFechaHora(LocalDateTime.now());
		Personal persona = notificacion.getEmisor();
		Integer maxNotificaciones = this.configuracionService.getNumMaxNotificaciones(); 
		
		if(persona != null) {
			
			Long n = (long) notificacionRepo.
					findByEmisorIdAndFechaHoraAfterAndTipoNotificacionIn(persona.getId(), LocalDateTime.now().minusDays(1), Arrays.asList(TipoNotificacion.NORMAL))
					.size();
			
			if(n>maxNotificaciones) {
				throw new NotificacionesLimitException("Ha excedido el limite diario de notificaciones");
			}
		}
		Notificacion result = notificacionRepo.save(notificacion);
		
		List<LineaEnviado> lenviado = receptores.stream().map(x ->new LineaEnviado(result,x)).collect(Collectors.toList());
		
		lineaEnviadoService.saveAll(lenviado);
		em.flush();
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<Notificacion> findNoLeidasPersonal(Personal p){
		return notificacionRepo.findNoLeidasForPersonal(p.getId());
	}
	
	@Transactional
	public void deleteAll() throws DataAccessException{
		notificacionRepo.deleteAll();
	}
}
