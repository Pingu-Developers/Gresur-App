package org.springframework.gresur.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/proveedor")
@RestController
public class ProveedorController {
	
	private final ProveedorService proveedorService;
	
	@Autowired
	public ProveedorController(ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}
	
	@GetMapping
	public Iterable<Proveedor> getAll(){
		return proveedorService.findAll();
	}
	
	@PostMapping
	public Proveedor newProveedor(@Valid @RequestBody Proveedor p){

		return proveedorService.save(p);
	}
	

}
