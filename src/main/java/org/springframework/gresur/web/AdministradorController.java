package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.ERol;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Rol;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.model.User;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*",maxAge = 3600 )
@RequestMapping("api/adm")
@RestController

public class AdministradorController {
		
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	EncargadoDeAlmacenService encargadoService;
	
	@Autowired
	DependienteService dependienteService;
	
	@Autowired
	TransportistaService transportistaService;
	
	private final AdministradorService admService;

	@Autowired
	public AdministradorController(AdministradorService admService) {
		this.admService = admService;
	}


@PostMapping("/add/administrador")
	@PreAuthorize("hasRole('ADMIN')")
	public Administrador addAdministrador(@RequestBody @Valid Administrador p) throws DataAccessException{
		
		String [] uvus = p.getName().split(" ");

		String nombre = uvus.length==3?uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase()+uvus[2].substring(0,3).toLowerCase()
				:uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase();

		Administrador adm = admService.save(p);
		//Creacion nuevo Usuario
		User user = new User(nombre, encoder.encode("123456"));
		Set<Rol> rolset = new HashSet<>();	
		Rol rol = new Rol();
		rol.setName(ERol.ROLE_ADMIN);
		rolset.add(rol);
		user.setRoles(rolset); 
		user.setPersonal(adm);
		userRepository.save(user);	
		
		return adm;
	}
	@PostMapping("/add/dependiente")
	@PreAuthorize("hasRole('ADMIN')")
	public Dependiente addDependiente(@RequestBody @Valid Dependiente p) throws DataAccessException{
		String [] uvus = p.getName().split(" ");

		String nombre = uvus.length==3?uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase()+uvus[2].substring(0,3).toLowerCase()
				:uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase();

		Dependiente dependiente =  admService.saveDependiente(p);
		//Creacion nuevo Usuario
		User user = new User(nombre, encoder.encode("123456"));
		Set<Rol> rolset = new HashSet<>();	
		Rol rol = new Rol();
		rol.setName(ERol.ROLE_DEPENDIENTE);
		rolset.add(rol);
		user.setRoles(rolset); 
		user.setPersonal(dependiente);
		userRepository.save(user);	
		
		return dependiente;
	}
	@PostMapping("/add/transportista")
	@PreAuthorize("hasRole('ADMIN')")
	public Transportista addTransportista(@RequestBody @Valid Transportista p) throws DataAccessException{
		String [] uvus = p.getName().split(" ");

		String nombre = uvus.length>=3?uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase()+uvus[2].substring(0,3).toLowerCase()
				:uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase();

		Transportista transportista =  admService.saveTransportista(p);
		//Creacion nuevo Usuario
		User user = new User(nombre, encoder.encode("123456"));
		Set<Rol> rolset = new HashSet<>();	
		Rol rol = new Rol();
		rol.setName(ERol.ROLE_TRANSPORTISTA);
		rolset.add(rol);
		user.setRoles(rolset); 
		user.setPersonal(transportista);
		userRepository.save(user);	
		
		return transportista;
	}
	@PostMapping("/add/encargado")
	@PreAuthorize("hasRole('ADMIN')")
	public EncargadoDeAlmacen addEncargadoDeAlmacen(@RequestBody EncargadoDeAlmacen p) throws DataAccessException{
		String [] uvus = p.getName().split(" ");

		String nombre = uvus.length==3?uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase()+uvus[2].substring(0,3).toLowerCase()
				:uvus[0].substring(0, 3).toLowerCase()+uvus[1].substring(0,3).toLowerCase();

		EncargadoDeAlmacen encargado =  admService.saveEncargadoDeAlmacen(p);
		//Creacion nuevo Usuario
		User user = new User(nombre, encoder.encode("123456"));
		Set<Rol> rolset = new HashSet<>();	
		Rol rol = new Rol();
		rol.setName(ERol.ROLE_ENCARGADO);
		rolset.add(rol);
		user.setRoles(rolset); 
		user.setPersonal(encargado);
		userRepository.save(user);	
		
		return encargado;
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
	@GetMapping("/personal/profile")
	public Personal findPersonalByNif(){
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		return per;
	}
	@PutMapping("/personal/profile")
	public Personal putPersonalByNif(@Valid @RequestBody Personal personalModificado){
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		Rol rol = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getRoles().iterator().next();
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		User u = userRepository.findById(per.getId()).orElse(null);
		u.getPersonal().setDireccion(personalModificado.getDireccion());
		u.getPersonal().setTlf(personalModificado.getTlf());
		u.getPersonal().setImage(personalModificado.getImage());
		u.getPersonal().setEmail(personalModificado.getEmail());
		userRepository.save(u);
	
		switch(rol.getName()) {
			case ROLE_ADMIN:
				Administrador adm = admService.findByNIF(per.getNIF());
					adm.setDireccion(personalModificado.getDireccion());
					adm.setTlf(personalModificado.getTlf());
					adm.setImage(personalModificado.getImage());
					adm.setEmail(personalModificado.getEmail());
					admService.save(adm);
			break;
			case ROLE_DEPENDIENTE:
				Dependiente dependiente = dependienteService.findByNIF(per.getNIF());
					dependiente.setDireccion(personalModificado.getDireccion());
					dependiente.setTlf(personalModificado.getTlf());
					dependiente.setImage(personalModificado.getImage());
					dependiente.setEmail(personalModificado.getEmail());
					admService.saveDependiente(dependiente);
			break;
			case ROLE_ENCARGADO:
				EncargadoDeAlmacen encargado = encargadoService.findByNIF(per.getNIF());
					encargado.setDireccion(personalModificado.getDireccion());
					encargado.setTlf(personalModificado.getTlf());
					encargado.setImage(personalModificado.getImage());
					encargado.setEmail(personalModificado.getEmail());
					admService.saveEncargadoDeAlmacen(encargado);
			break;
			case ROLE_TRANSPORTISTA:
				Transportista transportista = transportistaService.findByNIF(per.getNIF());
					transportista.setDireccion(personalModificado.getDireccion());
					transportista.setTlf(personalModificado.getTlf());
					transportista.setImage(personalModificado.getImage());
					transportista.setEmail(personalModificado.getEmail());
					admService.saveTransportista(transportista);
			break;
		}
		
		
		return per;
	}
	

	
	
}
