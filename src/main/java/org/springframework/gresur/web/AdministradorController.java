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
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

	private String createUvus(String []uvus) {
		String user = "";
		Integer n = uvus.length;
		if(n>=3) {
			String uvusNombre = uvus[0].length()>=3?uvus[0].substring(0,3).toLowerCase():uvus[0].length()==2?uvus[0].concat("x"):uvus[0].concat("xx");
			String uvusApellido1 = uvus[1].length()>=3?uvus[1].substring(0,3).toLowerCase():uvus[1].length()==2?uvus[1].concat("x"):uvus[1].concat("xx");
			String uvusApellido2 = uvus[2].length()>=3?uvus[2].substring(0,3).toLowerCase():uvus[2].length()==2?uvus[2].concat("x"):uvus[2].concat("xx");
			user = uvusNombre+uvusApellido1+uvusApellido2;
		}
		if(n==2) {
			String uvusNombre = uvus[0].length()>=3?uvus[0].substring(0,3).toLowerCase():uvus[0].length()==2?uvus[0].concat("x"):uvus[0].concat("xx");
			String uvusApellido1 = uvus[1].length()>=3?uvus[1].substring(0,3).toLowerCase():uvus[1].length()==2?uvus[1].concat("x"):uvus[1].concat("xx");
			user = uvusNombre+uvusApellido1+"xxx";
		}
		if(n==1) {
			String uvusNombre = uvus[0].length()>=3?uvus[0].substring(0,3).toLowerCase():uvus[0].length()==2?uvus[0].concat("x"):uvus[0].concat("xx");
			user = uvusNombre+"xxx"+"xxx";
		}
		return user;
	}
	
	@PostMapping("/add/administrador")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addAdministrador(@RequestBody @Valid Administrador p, BindingResult result) throws DataAccessException{
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		else {
			String [] uvus = p.getName().split(" ");

			String nombre = createUvus(uvus);
			
			Administrador adm = p;
			
			try {
				adm = admService.save(adm);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
			//Creacion nuevo Usuario
			User user = new User(nombre, encoder.encode("123456"));
			Set<Rol> rolset = new HashSet<>();	
			Rol rol = new Rol();
			rol.setName(ERol.ROLE_ADMIN);
			rolset.add(rol);
			user.setRoles(rolset); 
			user.setPersonal(adm);
			
			try {
				userRepository.save(user);
				return ResponseEntity.ok(user);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}
		
	@PostMapping("/add/dependiente")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addDependiente(@RequestBody @Valid Dependiente p, BindingResult result) throws DataAccessException{
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));		
		}
		
		else {
			String [] uvus = p.getName().split(" ");

			String nombre = createUvus(uvus);

			Dependiente d = p;

			try {
				d =  admService.saveDependiente(d);			
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
			//Creacion nuevo Usuario
			User user = new User(nombre, encoder.encode("123456"));
			Set<Rol> rolset = new HashSet<>();	
			Rol rol = new Rol();
			rol.setName(ERol.ROLE_DEPENDIENTE);
			rolset.add(rol);
			user.setRoles(rolset); 
			user.setPersonal(d);
			
			try {
				userRepository.save(user);	
				return ResponseEntity.ok(user);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}
	
	@PostMapping("/add/transportista")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTransportista(@RequestBody @Valid Transportista p, BindingResult result) throws DataAccessException{
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		else {
			String [] uvus = p.getName().split(" ");

			String nombre = createUvus(uvus);

			Transportista t = p;
			
			try {
				t =  admService.saveTransportista(t);			
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
			//Creacion nuevo Usuario
			User user = new User(nombre, encoder.encode("123456"));
			Set<Rol> rolset = new HashSet<>();	
			Rol rol = new Rol();
			rol.setName(ERol.ROLE_TRANSPORTISTA);
			rolset.add(rol);
			user.setRoles(rolset); 
			user.setPersonal(t);
			
			try {
				userRepository.save(user);	
				return ResponseEntity.ok(user);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}	
	}
	
	@PostMapping("/add/encargado")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addEncargadoDeAlmacen(@RequestBody @Valid EncargadoDeAlmacen p, BindingResult result) throws DataAccessException{
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		else {
			String [] uvus = p.getName().split(" ");

			String nombre = createUvus(uvus);
			
			EncargadoDeAlmacen encargado = p;
			
			try {
				encargado = admService.saveEncargadoDeAlmacen(encargado);		
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
			//Creacion nuevo Usuario
			User user = new User(nombre, encoder.encode("123456"));
			Set<Rol> rolset = new HashSet<>();	
			Rol rol = new Rol();
			rol.setName(ERol.ROLE_ENCARGADO);
			rolset.add(rol);
			user.setRoles(rolset); 
			user.setPersonal(encargado);
			
			try {
				userRepository.save(user);	
				return ResponseEntity.ok(user);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}		
		}		
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<Administrador> findAll(){
		return admService.findAll();
	}
	
	@GetMapping("/personal")
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findPersonalByNIF(@PathVariable("nif") String nif){
		
		String nifFormato = "^[0-9]{8}([A-Z]{1})?";
		
		if(!nif.matches(nifFormato)) {
			return ResponseEntity.badRequest().body("Formato del NIF invalido");
		}
		
		else {
			Personal p = admService.findByNIFPersonal(nif);
			return ResponseEntity.ok(p);
		}
	}
	
	@GetMapping("/personal/profile")
	@PreAuthorize("hasRole('ADMIN')")
	public Personal findPersonalLoggedIn(){
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		return per;
	}
	
	@PutMapping("/personal/profile")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> putPersonalByNif(@Valid @RequestBody Personal personalModificado, BindingResult result){
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		else {
			Authentication user = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
			Rol rol = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getRoles().iterator().next();
			Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
			
			User u = userRepository.findById(per.getId()).orElse(null);
			u.getPersonal().setDireccion(personalModificado.getDireccion());
			u.getPersonal().setTlf(personalModificado.getTlf());
			u.getPersonal().setImage(personalModificado.getImage());
			u.getPersonal().setEmail(personalModificado.getEmail());
			
			try {
				userRepository.save(u);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
			String error="";
		
			switch(rol.getName()) {
				case ROLE_ADMIN:
					Administrador adm = admService.findByNIF(per.getNIF());
						adm.setDireccion(personalModificado.getDireccion());
						adm.setTlf(personalModificado.getTlf());
						adm.setImage(personalModificado.getImage());
						adm.setEmail(personalModificado.getEmail());
						
						try {
							admService.save(adm);
						}
						catch(Exception e) {
							error = e.getMessage();
						}			
				break;
				
				case ROLE_DEPENDIENTE:
					Dependiente dependiente = dependienteService.findByNIF(per.getNIF());
						dependiente.setDireccion(personalModificado.getDireccion());
						dependiente.setTlf(personalModificado.getTlf());
						dependiente.setImage(personalModificado.getImage());
						dependiente.setEmail(personalModificado.getEmail());
						
						try {
							admService.saveDependiente(dependiente);
						}
						catch(Exception e) {
							error = e.getMessage();
						}	
				break;
				
				case ROLE_ENCARGADO:
					EncargadoDeAlmacen encargado = encargadoService.findByNIF(per.getNIF());
						encargado.setDireccion(personalModificado.getDireccion());
						encargado.setTlf(personalModificado.getTlf());
						encargado.setImage(personalModificado.getImage());
						encargado.setEmail(personalModificado.getEmail());
						try {
							admService.saveEncargadoDeAlmacen(encargado);
						}
						catch(Exception e) {
							error = e.getMessage();
						}	
				break;
				
				case ROLE_TRANSPORTISTA:
					Transportista transportista = transportistaService.findByNIF(per.getNIF());
						transportista.setDireccion(personalModificado.getDireccion());
						transportista.setTlf(personalModificado.getTlf());
						transportista.setImage(personalModificado.getImage());
						transportista.setEmail(personalModificado.getEmail());
						
						try {
							admService.saveTransportista(transportista);
						}
						catch(Exception e) {
							error = e.getMessage();
						}	
				break;
			}
			
			if(!error.isEmpty()) {
				return ResponseEntity.badRequest().body(error);
			}
			
			else {
				return ResponseEntity.ok(user);
			}
		}
	}		
}