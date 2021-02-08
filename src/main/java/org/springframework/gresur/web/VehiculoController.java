package org.springframework.gresur.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.TransportistaRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.PersonalService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple4;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@Autowired
	protected ITVService itvService;
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected ReparacionService reparacionService;
	
	@Autowired
	protected PersonalService<Transportista, TransportistaRepository> personalService;
	
	@Autowired
	protected UserRepository userRepository;
	
	
	@GetMapping
	@PreAuthorize("hasRole('TRANSPORTISTA')")
	public List<Tuple4<Vehiculo, String, String, String>> getVehiculosITVSeguroDisponibilidad(){
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
				
		Transportista t = (Transportista) per;
		
		Iterable<Vehiculo> iterableVehiculos = vehiculoService.findAll();
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		iterableVehiculos.forEach(listaVehiculos::add);
		
		List<ITV> listaITV = listaVehiculos.stream().map(x->itvService.findLastITVVehiculo(x.getMatricula())).collect(Collectors.toList());
		Map<Vehiculo, ResultadoITV> diccVehiculosITV = listaITV.stream().filter(x->x!=null).collect(Collectors.toMap(ITV::getVehiculo, ITV::getResultado));
		
		List<Seguro> listaSeguros = listaVehiculos.stream().map(x->seguroService.findLastSeguroByVehiculo(x.getMatricula())).collect(Collectors.toList());
		Map<Vehiculo, LocalDate> diccVehiculosSeguros = listaSeguros.stream().filter(x->x!=null).collect(Collectors.toMap(Seguro::getVehiculo, Seguro::getFechaExpiracion));
				
		List<Tuple4<Vehiculo, String, String, String>> listaDef = new ArrayList<Tuple4<Vehiculo, String, String, String>>();
		
		for (Vehiculo v: listaVehiculos) {
			
			Tuple4<Vehiculo, String, String, String> tp = new Tuple4<Vehiculo, String, String, String>();
			tp.setE1(v);
			
			
			ResultadoITV rITV = diccVehiculosITV.get(v);
			if(rITV==null) {
				tp.setE2("Sin ITV");
			}
			else {
				tp.setE2(rITV.toString());
			}
			
			
			LocalDate fechaExp = diccVehiculosSeguros.get(v);
			
			if(fechaExp==null) {
				tp.setE3("Sin seguro");
			}
			else {
				tp.setE3(fechaExp.isAfter(LocalDate.now())? "En Vigor" : "Caducado");
			}
			
			Boolean disponibilidad = vehiculoService.getDisponibilidad(v.getMatricula(), t);
			tp.setE4(disponibilidad? "DISPONIBLE" : "NO DISPONIBLE");
			
			tp.name1="vehiculo";
			tp.name2="itv";
			tp.name3="seguro";
			tp.name4="disponibilidad";

			listaDef.add(tp);
		}
		
		return listaDef;
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Tuple4<Vehiculo, List<Seguro>, List<ITV>, List<Reparacion>>> getAllVehiculos(){
		
		Iterable<Vehiculo> iterableVehiculos = vehiculoService.findAll();
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		iterableVehiculos.forEach(listaVehiculos::add);
		
		List<Tuple4<Vehiculo, List<Seguro>, List<ITV>, List<Reparacion>>> ldef = new ArrayList<Tuple4<Vehiculo,List<Seguro>,List<ITV>,List<Reparacion>>>();
		
		for (Vehiculo v: listaVehiculos) {
			
			Tuple4<Vehiculo, List<Seguro>, List<ITV>, List<Reparacion>> tp = new Tuple4<Vehiculo, List<Seguro>, List<ITV>, List<Reparacion>>();
					
			tp.setE1(v);
			
			List<Seguro> ls = seguroService.findByVehiculo(v.getMatricula());
			ls.sort(Comparator.comparing(Seguro::getFechaExpiracion).reversed());
			tp.setE2(ls);
			
			List<ITV> li = itvService.findByVehiculo(v.getMatricula());
			li.sort(Comparator.comparing(ITV::getExpiracion).reversed());
			tp.setE3(li);
			
			List<Reparacion> lr = reparacionService.findByMatricula(v.getMatricula());
			lr.sort(Comparator.comparing(Reparacion::getFechaEntradaTaller).reversed());
			tp.setE4(lr);
			
			tp.name1="vehiculo";
			tp.name2="seguros";
			tp.name3="itvs";
			tp.name4="reparaciones";
			
			ldef.add(tp);
		}
		
		return ldef;
	}
	
	@GetMapping("/allpaginado")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String,Object> getAllVehiculos(Pageable pageable){
		
		Page<Vehiculo> pagina = vehiculoService.findAll(pageable); 
		List<Vehiculo> listaVehiculos = pagina.toList();
	
		List<Tuple4<Vehiculo, List<Seguro>, List<ITV>, List<Reparacion>>> ldef = new ArrayList<Tuple4<Vehiculo,List<Seguro>,List<ITV>,List<Reparacion>>>();
		
		for (Vehiculo v: listaVehiculos) {
			
			Tuple4<Vehiculo, List<Seguro>, List<ITV>, List<Reparacion>> tp = new Tuple4<Vehiculo, List<Seguro>, List<ITV>, List<Reparacion>>();
					
			tp.setE1(v);
			
			List<Seguro> ls = seguroService.findByVehiculo(v.getMatricula());
			ls.sort(Comparator.comparing(Seguro::getFechaExpiracion).reversed());
			tp.setE2(ls);
			
			List<ITV> li = itvService.findByVehiculo(v.getMatricula());
			li.sort(Comparator.comparing(ITV::getExpiracion).reversed());
			tp.setE3(li);
			
			List<Reparacion> lr = reparacionService.findByMatricula(v.getMatricula());
			lr.sort(Comparator.comparing(Reparacion::getFechaEntradaTaller).reversed());
			tp.setE4(lr);
			
			tp.name1="vehiculo";
			tp.name2="seguros";
			tp.name3="itvs";
			tp.name4="reparaciones";
			
			ldef.add(tp);
		}
		
		Map<String,Object> res = new HashMap<>();
		res.put("articleDetails", ldef);
		res.put("totalPages", pagina.getTotalPages());
		res.put("totalElements", pagina.getTotalElements());
		return res;
	}
	
	@GetMapping("/allsimple")
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<Vehiculo> getAllVehiculosSimple(){
		Iterable<Vehiculo> iterableVehiculos = vehiculoService.findAll();
		return iterableVehiculos;
	}
	
	@PostMapping("/info")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getInfo(@RequestBody @Valid Vehiculo vehiculo, BindingResult result){
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
				
		try {
			Tuple2<Seguro,ITV> res = new Tuple2<>();
			res.setE1(seguroService.findLastSeguroByVehiculo(vehiculo.getMatricula()));
			res.setE2(itvService.findLastITVVehiculo(vehiculo.getMatricula()));
			res.name1 = "seguro";
			res.name2 = "itv";				
			return ResponseEntity.ok(res);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addVehiculo(@RequestBody @Valid Vehiculo vehiculo, BindingResult result){
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		try {
			Vehiculo v = vehiculoService.save(vehiculo);				
			return ResponseEntity.ok(v);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@GetMapping("/allTiposVehiculos")
	@PreAuthorize("hasRole('ADMIN')")
	public TipoVehiculo[] getAllTiposVehiculos(){
		return TipoVehiculo.values();
	}
	
	@DeleteMapping("/delete/{matricula}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteContrato(@PathVariable("matricula") String matricula) throws DataAccessException{
		
		if(!matricula.matches("[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}") || !matricula.matches("E[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}")) {
			return ResponseEntity.badRequest().body("Formato de matricula no valido");
		}
		else {
			try {
				vehiculoService.deleteByMatricula(matricula);
				return ResponseEntity.ok("Borrado realizado correctamente");
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}

}