package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehiculoController {

	private final VehiculoService vehiculoService;
	
	@Autowired
	public VehiculoController(VehiculoService vehiculoService) {
		this.vehiculoService = vehiculoService;
	}
}
