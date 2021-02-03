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
	public List<Estanteria> getFacturasCliente(){
	
		return estanteriaService.findAll();
	}
	
	@PutMapping("/update/{categoria}/{capacidad}")
	@PreAuthorize("hasRole('ENCARGADO')")
	public ResponseEntity<?> updateEstanteriaCapacidad(@PathVariable("categoria") Categoria cat, @PathVariable("capacidad") Double porcentajeCapacidad){
		try {
			Estanteria e = estanteriaService.findByCategoria(cat);
			e.setCapacidad(e.getAlmacen().getCapacidad() * (porcentajeCapacidad/100));
			estanteriaService.save(e);
			return ResponseEntity.ok("");
		} catch (Exception e) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n\n\n\n\n\n\n\n\n" + e);
			return ResponseEntity.badRequest().body(e);
		}
		
	}

}
