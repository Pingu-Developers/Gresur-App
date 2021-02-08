package org.springframework.gresur.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/estanterias")
public class EstanteriaController {
	private final EstanteriaService estanteriaService;
	
	@Autowired
	public EstanteriaController(EstanteriaService estanteriaService) {
		this.estanteriaService = estanteriaService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('ENCARGADO')")
	public List<Estanteria> getAllEstanterias(){
		return estanteriaService.findAll();
	}
	
	@PutMapping("/update/{categoria}/{capacidad}")
	@PreAuthorize("hasRole('ENCARGADO') or hasRole('ADMIN')")
	public ResponseEntity<?> updateEstanteriaCapacidad(@PathVariable("categoria") Categoria cat, @PathVariable("capacidad") Double porcentajeCapacidad){
		try {
			Estanteria est = estanteriaService.findByCategoria(cat);
			Double newCapacidad = (est.getAlmacen().getCapacidad() * (porcentajeCapacidad/100));
			est.setCapacidad(newCapacidad);
			Estanteria eDef = estanteriaService.save(est);
			return ResponseEntity.ok(eDef);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e);
		}
		
	}

}
