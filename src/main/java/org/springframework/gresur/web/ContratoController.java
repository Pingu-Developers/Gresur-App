package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.ContratoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContratoController {
	
	private final ContratoService contratoService;
	
	@Autowired
	public ContratoController(ContratoService contratoService) {
		this.contratoService = contratoService;
	}
}
