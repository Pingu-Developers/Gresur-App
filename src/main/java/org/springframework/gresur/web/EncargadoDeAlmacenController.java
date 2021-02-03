package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RequestMapping("api/encargado")
@RestController
public class EncargadoDeAlmacenController {
	

	@Autowired
	public AlmacenService almacenService;
	
	private final EncargadoDeAlmacenService encargadoService;
	
	@Autowired
	public EncargadoDeAlmacenController(EncargadoDeAlmacenService encargadoService) {
		this.encargadoService = encargadoService;
	}
	
	@GetMapping("/almacen")
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<Almacen> findAlmacenesDisponible(){
		List<EncargadoDeAlmacen> encargados = encargadoService.findAll();
		List<Almacen> ea = new ArrayList<Almacen>();
		List<Almacen> disponible = new ArrayList<Almacen>();
		for (int i = 0; i < encargados.size(); i++) {
			ea.add(encargados.get(i).getAlmacen());		
		}
		Iterable<Almacen> almacenes = almacenService.findAll();
		almacenes.forEach(disponible::add);
		disponible.removeAll(ea);

		return disponible;
	}

}
