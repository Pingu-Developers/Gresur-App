package org.springframework.gresur.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.service.DependienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dependiente")
@RestController
public class DependienteController{

	private final DependienteService dependienteService;
	
	@Autowired
	public DependienteController(DependienteService dependienteService) {
		this.dependienteService= dependienteService;
	}
	
	@PostMapping
	public Dependiente add(@RequestBody @Valid Dependiente dependiente) throws DataAccessException{
		return dependienteService.save(dependiente);
	}
	
}
