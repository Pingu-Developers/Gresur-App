package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple3;
import org.springframework.gresur.util.Tuple4;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RequestMapping("api/almacen")
@RestController
@Slf4j
public class AlmacenController {
	
	private AlmacenService almacenService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private EstanteriaService zonaService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	public AlmacenController(AlmacenService almacenService) {
		this.almacenService = almacenService;
	}
	
	
	@GetMapping("/gestion")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Tuple3<Almacen, Double, List<Tuple2<Categoria, Double>>>> getOcupacionGestionStock(){
		List<Tuple3<Almacen, Double, List<Tuple2<Categoria, Double>>>> res = new ArrayList<>();
		Iterable<Almacen> almacenes = almacenService.findAll();
		for(Almacen alm: almacenes) {
			Tuple3<Almacen, Double, List<Tuple2<Categoria, Double>>> tupla = new Tuple3<Almacen, Double, List<Tuple2<Categoria,Double>>>();
			tupla.setE1(alm);
			tupla.setE2(getOcupacionTotalAlmacen(alm)*100);
			tupla.setE3(getCategoriasCapacidad(alm));
			
			tupla.name1 = "almacen";
			tupla.name2 = "ocupacionAlmacen";
			tupla.name3 = "categorias";
			
			res.add(tupla);
			
		}
		return res;
	}
	
	@GetMapping("/gestionEncargado/{almacenAdm}")
	@PreAuthorize("hasRole('ENCARGADO') or hasRole('ADMIN')")
	public List<Tuple4<Categoria, Double, Double,Integer>> ocupacionPorEstanteria(@PathVariable("almacenAdm") Long almId){
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Almacen alm;
		
		if(almId == -1) {
			EncargadoDeAlmacen encargado = (EncargadoDeAlmacen) userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal(); 
			alm = encargado.getAlmacen();
		} else {
			alm = almacenService.findById(almId);
		}
		
		List<Tuple2<Categoria, Double>> tupla = this.getCategoriasCapacidad2(alm);
		List<Tuple4<Categoria, Double, Double, Integer>> res = new ArrayList<Tuple4<Categoria,Double,Double, Integer>>();
		for(Tuple2<Categoria, Double> tp: tupla) {
			Estanteria est = zonaService.findByCategoria(tp.getE1());
			
			if(est != null) {
				Double porcentajeAlmacen = est.getCapacidad()/alm.getCapacidad()*100;
				Tuple4<Categoria, Double, Double,Integer> terna = new Tuple4<Categoria, Double, Double, Integer>();
				
				terna.name1 = "categoria";
				terna.name2 = "ocupacionEstanteria";
				terna.name3 = "porcentajeAlmacen";
				terna.name4 = "version";
				
				terna.setE1(tp.getE1());
				terna.setE2(tp.getE2());
				terna.setE3(porcentajeAlmacen);
				terna.setE4(alm.getVersion());
				res.add(terna);
			}
		}
		return res;
	}

	
	private List<Tuple2<Categoria, Double>> getCategoriasCapacidad(Almacen alm) {
		List<Tuple2<Categoria, Double>> res = new ArrayList<>();
		List<Categoria> categorias =  zonaService.findAllEstanteriaByAlmacen(alm.getId()).stream().map(x -> x.getCategoria()).collect(Collectors.toList());
		for(Categoria cat: categorias) {
			Tuple2<Categoria, Double> tupla = new Tuple2<Categoria, Double>();
			
			Double ocupacionProductosPorEstanteria = productoService.findByEstanteria(cat)
			.stream()
			.map(x -> x.getAlto() * x.getAncho() * x.getProfundo() * x.getStock())
			.mapToDouble(x -> x).sum();
			
			Double capacidadTotalPorEstanteria = zonaService.findAllEstanteriaByAlmacen(alm.getId())
			.stream()
			.filter(x -> x.getCategoria().equals(cat))
			.mapToDouble(x -> x.getCapacidad()).sum();
			
			if(capacidadTotalPorEstanteria == 0) {
				tupla.setE2(0.0);
			} else {
				tupla.setE2((ocupacionProductosPorEstanteria/capacidadTotalPorEstanteria)*100);

			}
			
			tupla.setE1(cat);
			
			tupla.name1 = "categoria";
			tupla.name2 = "ocupacionEstanteria";
			
			res.add(tupla);
		}
		return res;
	}
	
	@GetMapping("/categorias/{almacenAdm}")
	@PreAuthorize("hasRole('ENCARGADO') or hasRole('ADMIN')")
	public List<Categoria> getCategorias(@PathVariable("almacenAdm") Long almId){
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		List<Estanteria> estanterias;
		
		if(almId == -1) {
			EncargadoDeAlmacen encargado = (EncargadoDeAlmacen) userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
			estanterias =  zonaService.findAllEstanteriaByAlmacen(encargado.getAlmacen().getId());
		} else {
			estanterias =  zonaService.findAllEstanteriaByAlmacen(almId);
		}
		return estanterias.stream().map(x -> x.getCategoria()).collect(Collectors.toList());
	}
	
	private List<Tuple2<Categoria, Double>> getCategoriasCapacidad2(Almacen alm) {
		
		List<Tuple2<Categoria, Double>> res = new ArrayList<>();
		
		List<Estanteria> estanterias =  zonaService.findAllEstanteriaByAlmacen(alm.getId());
		
		for(Estanteria est: estanterias) {
			
			Tuple2<Categoria, Double> tupla = new Tuple2<Categoria, Double>();
			
			Double ocupacionProductosPorEstanteria = productoService.findByEstanteria(est.getCategoria())
			.stream()
			.map(x -> (x.getAlto() * x.getAncho() * x.getProfundo() * x.getStock())/est.getCapacidad() * 100)
			.mapToDouble(x -> x).sum();
			
			tupla.setE1(est.getCategoria());
			tupla.setE2(ocupacionProductosPorEstanteria);
			
			tupla.name1 = "categoria";
			tupla.name2 = "ocupacionEstanteria";
			
			res.add(tupla);
		}
		return res;
	}

	private Double getOcupacionTotalAlmacen(Almacen alm) {
		Double capacidadTotal = alm.getCapacidad();
		Double productosOcupacion = productoService.findAll().stream()
		.filter(x -> x.getEstanteria().getAlmacen().equals(alm))
		.map(x -> x.getAlto() * x.getAncho() * x.getProfundo() * x.getStock())
		.mapToDouble(x -> x).sum();
		
		return productosOcupacion/capacidadTotal;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<Almacen> findAll(){
		return almacenService.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addAlmacen(@RequestBody @Valid Almacen alm, BindingResult result){
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/almacen Constrain violation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		else {
			try {
				Almacen a = almacenService.save(alm);
				log.info("/almacen Entity Almacen id: "+a.getId()+" was created");
				return ResponseEntity.ok(a);
			}catch(Exception e) {
				log.error("/almacen " + e.getMessage());
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}
}