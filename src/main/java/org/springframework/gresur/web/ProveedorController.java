package org.springframework.gresur.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/proveedor")
@RestController
@Slf4j
public class ProveedorController {
	
	private final ProveedorService proveedorService;
	
	@Autowired
	public ProveedorController(ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<Proveedor> getAll(){
		return proveedorService.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> newProveedor(@Valid @RequestBody Proveedor p, BindingResult result){
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/proveedor/ Constrain violations in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		try {
			Proveedor pDef = proveedorService.save(p);
			log.info("/proveedor/ Entity Proveedor with id: "+pDef.getId()+" was created successfully");
			return ResponseEntity.ok(pDef);
		}catch(Exception e) {
			log.error("/proveedor/ "+ e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}