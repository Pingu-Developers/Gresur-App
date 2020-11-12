package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProveedorController {
	
	private final ProveedorService proveedorService;
	
	@Autowired
	public ProveedorController(ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}
}
