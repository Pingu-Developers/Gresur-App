package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacturaEmitidaController {
	
	private final FacturaEmitidaService facturaEmitidaService;

	@Autowired
	public FacturaEmitidaController(FacturaEmitidaService facturaEmitidaService) {
		this.facturaEmitidaService = facturaEmitidaService;
	}
	

}
