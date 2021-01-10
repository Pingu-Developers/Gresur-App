package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*",maxAge = 3600 )
@RequestMapping("api/adm")
@RestController

public class AdministradorController {
		
	private final AdministradorService admService;

	@Autowired
	public AdministradorController(AdministradorService admService) {
		this.admService = admService;
	}

	
	@PostMapping("/add/administrador")
	@PreAuthorize("hasRole('ADMIN')")
	public Administrador addAdministrador(@RequestBody @Valid Administrador p) throws DataAccessException{
		return admService.save(p);
	}
	@PostMapping("/add/dependiente")
	@PreAuthorize("hasRole('ADMIN')")
	public Dependiente addDependiente(@RequestBody @Valid Dependiente p) throws DataAccessException{
		return admService.saveDependiente(p);
	}
	@PostMapping("/add/transportista")
	@PreAuthorize("hasRole('ADMIN')")
	public Transportista addTransportista(@RequestBody @Valid Transportista p) throws DataAccessException{
		return admService.saveTransportista(p);
	}
	@PostMapping("/add/encargado")
	@PreAuthorize("hasRole('ADMIN')")
	public EncargadoDeAlmacen addEncargadoDeAlmacen(@RequestBody EncargadoDeAlmacen p) throws DataAccessException{
		return admService.saveEncargadoDeAlmacen(p);
	}

	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<Administrador> findAll(){
		return admService.findAll();
	}

	
	@GetMapping("/personal")
	public HashMap<String, List<Personal>> findAllPersonal() {

		HashMap<String, List<Personal>> dic = new HashMap<String, List<Personal>>();
		List<Personal> empleados = admService.findAllPersonal();
		
		List<Personal> t = new ArrayList<Personal>();
		List<Personal> d = new ArrayList<Personal>();
		List<Personal> ea = new ArrayList<Personal>();
		List<Personal> adm = new ArrayList<Personal>();

		for (int i = 0; i < empleados.size(); i++) {
			Personal p = empleados.get(i);
			String clase = p.getClass().getName().replace("org.springframework.gresur.model.", "").toString().toLowerCase();
			switch (clase) {
			case "administrador":
				adm.add(p);
				dic.put(clase, adm);
				break;
			case "encargadodealmacen":
				ea.add(p);
				dic.put(clase, ea);
				break;
			case "transportista":
				t.add(p);
				dic.put(clase, t);
				break;
			default:
				d.add(p);
				dic.put(clase, d);
				break;
			}
		}

		return dic;
	}
	
	@GetMapping("/personal/{nif}")
	public Personal findPersonal(@PathVariable("nif") String nif){
		return admService.findByNIFPersonal(nif);
	}
	

	
	
}
