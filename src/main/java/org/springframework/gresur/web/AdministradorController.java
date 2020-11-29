package org.springframework.gresur.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/adm")
@RestController

public class AdministradorController {
	
	private final AdministradorService admService;
	
	@Autowired
	public AdministradorController(AdministradorService admService) {
		this.admService = admService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Administrador add(@RequestBody @Valid Administrador adm) throws DataAccessException{
		return admService.save(adm);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<Administrador> findAll(){
		return admService.findAll();
	}
	
	@GetMapping("/personal")
	public Iterable<Personal> findAllPersonal(){
		
		return admService.findAllPersonal();
	}
	
	@GetMapping("/personal/{id}")
	public Personal findPersonal(@PathVariable("id") Long id){
		System.out.println(id);
		return admService.findPersonal(id);
	}
}
