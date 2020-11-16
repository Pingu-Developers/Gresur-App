package org.springframework.gresur.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/adm")
@RestController
public class AdministradorController {
	
	private final AdministradorService admService;
	
	@Autowired
	public AdministradorController(AdministradorService admService) {
		this.admService = admService;
	}
	
	@PostMapping
	public Administrador add(@RequestBody @Valid Administrador adm) throws DataAccessException{
		return admService.add(adm);
	}
	
	@GetMapping
	public Iterable<Administrador> findAll(){
		return admService.findAll();
	}
}
