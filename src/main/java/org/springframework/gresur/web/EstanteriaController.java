package org.springframework.gresur.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.service.AlmacenService;
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
	private AlmacenService almacenService;
	
	@Autowired
	public EstanteriaController(EstanteriaService estanteriaService) {
		this.estanteriaService = estanteriaService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('ENCARGADO')")
	public List<Estanteria> getAllEstanterias(){
		return estanteriaService.findAll();
	}
	
	@PutMapping("/update/{categoria}/{capacidad}/{version}")
	@PreAuthorize("hasRole('ENCARGADO') or hasRole('ADMIN')")
	public ResponseEntity<?> updateEstanteriaCapacidad(@PathVariable("categoria") Categoria cat, @PathVariable("capacidad") Double porcentajeCapacidad, @PathVariable("version") Integer version){
		
		Estanteria est = estanteriaService.findByCategoria(cat);

		if(est==null) {
			return ResponseEntity.badRequest().body("Estanteria no encontrada");
		}
		
		Almacen a = est.getAlmacen();
				
		if(a.getVersion()!=version) {
			return ResponseEntity.badRequest().body("Concurrent modification. Please reload");
		}
		
		try {
			Double newCapacidad = (est.getAlmacen().getCapacidad() * (porcentajeCapacidad/100));
			est.setCapacidad(newCapacidad);
			Estanteria eDef = estanteriaService.save(est);
			
			String s = a.getDireccion().substring(a.getDireccion().length()-1);
						
			if(s.equals(".")) {
				a.setDireccion(a.getDireccion().substring(0, a.getDireccion().length()-1));
			}
			else { 
				a.setDireccion(a.getDireccion()+".");
			}
			
			almacenService.save(a);
			
			return ResponseEntity.ok(eDef);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

}
