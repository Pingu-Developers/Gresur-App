package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*",maxAge = 3600 )
@RequestMapping("api/contrato")
@RestController
public class ContratoController {
	
	private final ContratoService contratoService;
	@Autowired
	private AdministradorService admService;
	
	@Autowired
	public ContratoController(ContratoService contratoService) {
		this.contratoService = contratoService;
	}

	@GetMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public HashMap<String, List<Contrato>> findAllPersonal() {

		HashMap<String, List<Contrato>> dic = new HashMap<String, List<Contrato>>();
		List<Contrato> contratos = contratoService.findAll();
		
		List<Contrato> t = new ArrayList<Contrato>();
		List<Contrato> d = new ArrayList<Contrato>();
		List<Contrato> ea = new ArrayList<Contrato>();
		List<Contrato> adm = new ArrayList<Contrato>();

		for (int i = 0; i < contratos.size(); i++) {
			Personal p = contratos.get(i).getPersonal();
			Contrato c = contratos.get(i);
			String clase = p.getClass().getName().replace("org.springframework.gresur.model.", "").toString().toLowerCase();
			switch (clase) {
			case "administrador":
				adm.add(c);
				dic.put(clase, adm);
				break;
			case "encargadodealmacen":
				ea.add(c);
				dic.put(clase, ea);
				break;
			case "transportista":
				t.add(c);
				dic.put(clase, t);
				break;
			default:
				d.add(c);
				dic.put(clase, d);
				break;
			}
		}

		return dic;
	}
	
	@PostMapping("/add/{nif}")
	@PreAuthorize("hasRole('ADMIN')")
	public Contrato addContrato( @RequestBody Contrato c,@PathVariable("nif") String nif) throws DataAccessException{
		Personal p = admService.findByNIFPersonal(nif);
		c.setPersonal(p);
		Contrato contrato = contratoService.save(c);
		return contrato;
	}
}
