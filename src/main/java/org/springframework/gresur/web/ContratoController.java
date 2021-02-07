package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@CrossOrigin( origins = "*",maxAge = 3600 )
@RequestMapping("api/contrato")
@RestController
public class ContratoController {
	
	private final ContratoService contratoService;
	@Autowired
	private AdministradorService admService;
	
	@Autowired
	public ContratoController(ContratoService contratoService) {
		this.contratoService = contratoService;
	}

	@GetMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public HashMap<String, List<Contrato>> findAllPersonal() {

		HashMap<String, List<Contrato>> dic = new HashMap<String, List<Contrato>>();
		List<Contrato> contratos = contratoService.findAll();
		
		List<Contrato> t = new ArrayList<Contrato>();
		List<Contrato> d = new ArrayList<Contrato>();
		List<Contrato> ea = new ArrayList<Contrato>();
		List<Contrato> adm = new ArrayList<Contrato>();

		for (int i = 0; i < contratos.size(); i++) {
			Personal p = contratos.get(i).getPersonal();
			Contrato c = contratos.get(i);
			String clase = p.getClass().getName().replace("org.springframework.gresur.model.", "").toString().toLowerCase();
			switch (clase) {
			case "administrador":
				adm.add(c);
				dic.put(clase, adm);
				break;
			case "encargadodealmacen":
				ea.add(c);
				dic.put(clase, ea);
				break;
			case "transportista":
				t.add(c);
				dic.put(clase, t);
				break;
			default:
				d.add(c);
				dic.put(clase, d);
				break;
			}
		}

		return dic;
	}
	
	//TODO OJO: REVISAR FALLOS CON EL @Valid al hacer peticion
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
				return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
			}
			
			else {
				try {
					Contrato contrato = contratoService.save(cdef);
					return ResponseEntity.ok(contrato);
				}catch(Exception e) {
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
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
		
		if(!nif.matches(nifFormato)) {
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
		
		else {
			Contrato contratoOld = contratoService.findByPersonalNIF(nif);

			contratoOld.setEntidadBancaria(c.getEntidadBancaria());
			contratoOld.setFechaFin(c.getFechaFin());
			contratoOld.setFechaInicio(c.getFechaInicio());
			contratoOld.setNomina(c.getNomina());
			contratoOld.setTipoJornada(c.getTipoJornada());
			contratoOld.setObservaciones(c.getObservaciones());
			
			try {
				Contrato contratoDef = contratoService.save(contratoOld);				
				return ResponseEntity.ok(contratoDef);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}
	
	//TODO REVISAR BORRADO VALOR QUE DEVUELVE?
	@DeleteMapping("/delete/{nif}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteContrato(@PathVariable("nif") String nif) throws DataAccessException{
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
		
		if(!nif.matches(nifFormato)) {
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
		
		if(admService.findByNIFPersonal(nif)==null) {
			return ResponseEntity.badRequest().body("No existe ningun empleado con dicho NIF");
		}
		
		else {
			try {
				contratoService.deleteByPersonalNIF(nif);
				return ResponseEntity.ok("Borrado correctamente");
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}
	
}