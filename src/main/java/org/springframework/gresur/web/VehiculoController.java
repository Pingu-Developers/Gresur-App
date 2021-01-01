package org.springframework.gresur.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/vehiculo")
@RestController
public class VehiculoController {

	private final VehiculoService vehiculoService;
	
	@Autowired
	public VehiculoController(VehiculoService vehiculoService) {
		this.vehiculoService = vehiculoService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('TRANSPORTISTA')")
	public Pair<Iterable<Vehiculo>, List<TipoVehiculo>> getVehiculos(){
		return Pair.of(vehiculoService.findAll(), Arrays.asList(TipoVehiculo.values()));
	} 
	
}
