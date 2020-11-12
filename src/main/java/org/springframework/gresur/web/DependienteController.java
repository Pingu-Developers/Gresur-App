package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.DependienteService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DependienteController{

	private final DependienteService dependienteService;
	
	@Autowired
	public DependienteController(DependienteService dependienteService) {
		this.dependienteService= dependienteService;
	}
	
	
	
}
