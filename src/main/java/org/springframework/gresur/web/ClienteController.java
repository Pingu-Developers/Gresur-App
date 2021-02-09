package org.springframework.gresur.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/cliente")
@Slf4j
public class ClienteController {

	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public Iterable<Cliente> getClientes(){
		return clienteService.findAll();
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public ResponseEntity<?> add(@RequestBody @Valid Cliente cliente, BindingResult result) throws DataAccessException{
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/cliente/add Constrain violation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		else {
			try {
				Cliente c = clienteService.save(cliente);
				log.info("/cliente/add Entity Cliente with id: "+c.getId()+" was created successfully");
				return ResponseEntity.ok(c);
			}catch(Exception e) {
				log.error("/cliente/add " + e.getMessage());
				return ResponseEntity.badRequest().body(e.getMessage());
			}			
		}		
	}
	
	@GetMapping("/{NIF}/isDefaulter")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public ResponseEntity<?> findClienteIsDefaulterByNIF(@PathVariable("NIF") String NIF) throws DataAccessException{
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
		
		if(!NIF.matches(nifFormato)) {
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
		
		else {
			Cliente cliente = clienteService.findByNIF(NIF);
			
			if(cliente == null) {
				return ResponseEntity.badRequest().body("El NIF no es válido o no existe nadie con ese NIF en la base de datos");
			}
			
			else { 
				Boolean b = clienteService.isDefaulter(cliente);
				return ResponseEntity.ok(b);
			}
		}
	}
	
	@GetMapping("/{NIF}")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public ResponseEntity<?> findClienteByNIF(@PathVariable("NIF") String NIF) throws DataAccessException{
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
		
		if(!NIF.matches(nifFormato)) {
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
		
		else {
			Cliente c = clienteService.findByNIF(NIF);
			return ResponseEntity.ok(c);
		} 
	}
	
}