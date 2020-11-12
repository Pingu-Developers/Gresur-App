package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacturaRecibidaController {

	private final FacturaRecibidaService facturaRecibidaService;
	
	@Autowired
	public FacturaRecibidaController(FacturaRecibidaService facturaRecibidaService) {
		this.facturaRecibidaService= facturaRecibidaService;
	}
	
}
