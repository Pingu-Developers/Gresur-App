package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdministradorController {
	
	private final AdministradorService admService;
	
	@Autowired
	public AdministradorController(AdministradorService admService) {
		this.admService = admService;
	}
}
