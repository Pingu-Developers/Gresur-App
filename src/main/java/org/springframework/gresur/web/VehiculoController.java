package org.springframework.gresur.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.TransportistaRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.PersonalService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.util.Tuple4;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Autowired
	protected ITVService itvService;
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected PersonalService<Transportista, TransportistaRepository> personalService;
	
	@Autowired
	protected UserRepository userRepository;
	
	
	@GetMapping()
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
}
