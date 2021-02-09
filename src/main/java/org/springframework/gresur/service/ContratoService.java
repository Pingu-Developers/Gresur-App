package org.springframework.gresur.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.repository.ContratoRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.exceptions.SalarioMinimoException;
import org.springframework.gresur.util.FechaInicioFinValidation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContratoService {
	
	@PersistenceContext
	private EntityManager em;
	
	private ContratoRepository contratoRepository;
	
	private UserRepository userRepo;
	
	@Autowired
	private ConfiguracionService configService;
	
	@Autowired
	private NotificacionService notiService;
	
	@Autowired
	private AdministradorService adminService;
	
	@Autowired
	public ContratoService(ContratoRepository contratoRepository,UserRepository userRepo) {
		this.contratoRepository = contratoRepository;
		this.userRepo = userRepo;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	
	@Transactional(readOnly = true)
	public List<Contrato> findAll() throws DataAccessException{
		return contratoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Contrato> findLastContratoAllEmpleados() throws DataAccessException{
		return contratoRepository.findLastContratoAllEmpleados();
	}
	
	@Transactional(readOnly = true)
	public List<Contrato> findLastContratoRol(String rol) throws DataAccessException{
		switch(rol) {
			case "DEPENDIENTE":
				return contratoRepository.findLastContratoDependiente();
			case "TRANSPORTISTA":
				return contratoRepository.findLastContratoTransportista();
			case "ADMINISTRADOR":
				return contratoRepository.findLastContratoAdministrador();
			case "ENCARGADO":
				return contratoRepository.findLastContratoEncargado();
			default:
				throw new IllegalArgumentException("invalid rol");
		}
	}
	
	@Transactional(readOnly = true)
	public Contrato findById(Long id) throws DataAccessException{
		return contratoRepository.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public Contrato findByPersonalNIF(String nif) throws DataAccessException{
		return contratoRepository.findByPersonalNIF(nif).orElse(null);
	}
	
	@Transactional
	public Contrato save(Contrato contrato) throws DataAccessException{
		em.clear();
		
		LocalDate fechaInicio = contrato.getFechaInicio();
		LocalDate fechaFin = contrato.getFechaFin();
		
		if(contrato.getNomina() < configService.getSalarioMinimo()) {
			throw new SalarioMinimoException("El salario es menor que el salario minimo");
		} 

		FechaInicioFinValidation.fechaInicioFinValidation(Contrato.class,fechaInicio, fechaFin);
		
		Contrato ret = contratoRepository.save(contrato);
		em.flush();
		return ret;
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		contratoRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteAll() {
		contratoRepository.deleteAll();
	}
	
	@Transactional
	public void deleteByPersonalNIF(String NIF) {
		userRepo.deleteByPersonalNIF(NIF);
		contratoRepository.deleteByPersonalNIF(NIF);
	}
	
	@Transactional
	public List<Contrato> findAllCaducados() {
		return contratoRepository.findAllCaducados();
	}
	
	@Transactional
	public List<Contrato> findAllCaducanEnCincoDias() {
		return contratoRepository.findAllCaducanEnCincoDias();
	}

	@Transactional
	public Long count() {
		return contratoRepository.count();
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Scheduled(cron = "0 0 7 * * *")
	@Transactional
	public void actualizaContratos() {
		try {
			List<Contrato> caducados = this.findAllCaducados();
			List<Contrato> porCaducar = this.findAllCaducanEnCincoDias();
			List<Personal> receptores = adminService.findAll().stream().map( x-> (Personal) x).collect(Collectors.toList());
			
			for(Contrato c : caducados) {
				//Elimina el contrato
				this.deleteByPersonalNIF(c.getPersonal().getNIF());
				
				//Envia notificacion
				Notificacion noti = new Notificacion();
				noti.setCuerpo("El contrato de " + c.getPersonal().getName() + " ha expirado");
				noti.setEmisor(null);
				noti.setTipoNotificacion(TipoNotificacion.SISTEMA);
				noti.setFechaHora(LocalDateTime.now());
				notiService.save(noti, receptores);
			}
			for(Contrato c : porCaducar) {
		
				//Envia notificacion
				Notificacion noti = new Notificacion();
				noti.setCuerpo("El contrato de " + c.getPersonal().getName() + " expira en " + ChronoUnit.DAYS.between(LocalDate.now(), c.getFechaFin()) + " día(s)");
				noti.setEmisor(null);
				noti.setTipoNotificacion(TipoNotificacion.SISTEMA);
				noti.setFechaHora(LocalDateTime.now());
				notiService.save(noti, receptores);
			}
			log.info("Contratos actualizados");
			
		} catch(Exception e) {
			log.error("No ha sido posible actualizar los contratos: \n" + e.getMessage());
		}
	}
}
