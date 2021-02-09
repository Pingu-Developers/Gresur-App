package org.springframework.gresur.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin( origins = "*",maxAge = 3600 )
@RequestMapping("api/contrato")
@RestController
@Slf4j
public class ContratoController {
	
	private final ContratoService contratoService;
	@Autowired
	private AdministradorService admService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	public ContratoController(ContratoService contratoService) {
		this.contratoService = contratoService;
	}

	@GetMapping("/{rol}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findPersonal(@PathVariable("rol") String rol) {
		if(rol.equals("TODOS")) {
			return ResponseEntity.ok(contratoService.findLastContratoAllEmpleados());
		} else if(rol.equals("ENCARGADO") || rol.equals("DEPENDIENTE") || rol.equals("ADMINISTRADOR") || rol.equals("TRANSPORTISTA")) {
			return ResponseEntity.ok(contratoService.findLastContratoRol(rol));
		} else {
			return ResponseEntity.badRequest().body("Rol invalido");
		}
	}
	
	@PostMapping("/add/{nif}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addContrato(@RequestBody Contrato c, @PathVariable("nif") String nif, BindingResult result) throws DataAccessException{
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
		
		if(!nif.matches(nifFormato)) {
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
				
		Personal p = admService.findByNIFPersonal(nif);
		
		if(p==null) {
			return ResponseEntity.badRequest().body("NIF no perteneciente a ningun empleado");
		}
		
		else {
			c.setPersonal(p);
			
			@Valid 
			Contrato cdef = c;
			
			if(result.hasErrors()) {
				List<FieldError> le = result.getFieldErrors();
				if(contratoService.findByPersonalNIF(nif) == null) {
					log.info("Rollback of Personal nif: "+nif+" done successfully");
					admService.deleteByNIFPersonal(nif);
				}
				log.warn("/contrato/add Constrain violation in params");
				return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
			}
			
			else {
				try {
					Contrato contrato = contratoService.save(cdef);
					log.info("Entity Contrato with id: "+contrato.getId()+" was created successfully");
					return ResponseEntity.ok(contrato);
				}catch(Exception e) {
					if(contratoService.findByPersonalNIF(nif) == null) {
						log.info("Rollback of Personal nif: "+nif+" done successfully");
						admService.deleteByNIFPersonal(nif);
					}
					log.error("/contrato/add/{nif} " + e.getMessage());
					return ResponseEntity.badRequest().body(e.getMessage());
				}	
			}
		}
	}
	
	@PutMapping("/update/{nif}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateContrato(@RequestBody @Valid Contrato c,@PathVariable("nif") String nif, BindingResult result) throws DataAccessException{
				
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/contrato/update/{nif} Constrain violation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}	
		
		Contrato contratoOld = contratoService.findByPersonalNIF(nif);
		
		if(contratoOld==null) {
			return ResponseEntity.badRequest().body("El contrato que se intenta editar no existe");
		}
		
		if(contratoOld.getVersion()!=c.getVersion()) {
			log.error("/contrato/update/{nif} Concurrent modification");
			return ResponseEntity.badRequest().body("Concurrent modification");
		}
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
	
		if(!nif.matches(nifFormato)) {
			log.warn("/contrato/update/{nif} Constrain violation in params");
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
		else {
			try {
				contratoOld.setEntidadBancaria(c.getEntidadBancaria());
				contratoOld.setFechaFin(c.getFechaFin());
				contratoOld.setFechaInicio(c.getFechaInicio());
				contratoOld.setNomina(c.getNomina());
				contratoOld.setTipoJornada(c.getTipoJornada());
				contratoOld.setObservaciones(c.getObservaciones());
			
				Contrato contratoDef = contratoService.save(contratoOld);				
				log.info("/contrato/update/{nif} Entity Contrato with id: "+contratoDef.getId()+" update successfully");
				return ResponseEntity.ok(contratoDef);
			}catch(Exception e) {
				log.error("/contrato/update/{nif} " + e.getMessage());
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}
	
	@DeleteMapping("/delete/{nif}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteContrato(@PathVariable("nif") String nif) throws DataAccessException{
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		if(!nif.matches(nifFormato)) {
			log.warn("/contrato/delete/{nif} Constrain violation in params");
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
		if(admService.findByNIFPersonal(nif)==null) {
			return ResponseEntity.badRequest().body("No existe ningun empleado con dicho NIF");
		}
		else {
			try {
				Personal p = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
				
				if(p.getNIF().equals(nif)) {
					return ResponseEntity.badRequest().body("No se puede eliminar a si mismo");
				}
				
				contratoService.deleteByPersonalNIF(nif);
				log.info("/contrato/delete/{nif} Entities Contrato with personal.Nif: "+ nif + " deleted successfully");
				return ResponseEntity.ok("Borrado correctamente");
			}catch(Exception e) {
				log.error("/contrato/delete/{nif} " + e.getMessage());
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}
	
}