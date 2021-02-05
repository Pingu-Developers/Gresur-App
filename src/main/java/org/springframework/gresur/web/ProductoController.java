package org.springframework.gresur.web;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/producto")
@RestController
public class ProductoController {
	
	private final ProductoService productoService;
	
	private final NotificacionService notificacionService;
	
	private final AdministradorService adminService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	public ProductoController(ProductoService productoService,NotificacionService notificacionService,AdministradorService adminService) {
		this.productoService = productoService;
		this.notificacionService = notificacionService;
		this.adminService = adminService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('DEPENDIENTE') or hasRole('ENCARGADO')")
	public Map<Categoria,List<Producto>> findProductos(){
		//return Pair.of(productoService.findAll(), Arrays.asList(Categoria.values()));
		List<Producto> lp = productoService.findAll();
		Map<Categoria,List<Producto>> diccVehiculoProductos = new HashMap<Categoria, List<Producto>>();
		
		for (int i = 0; i < lp.size(); i++) {
			Categoria c = lp.get(i).getEstanteria().getCategoria(); //clave
			Producto p = lp.get(i); //valor
			
			if(diccVehiculoProductos.containsKey(c)) {
				List<Producto> l = diccVehiculoProductos.get(c);
				l.add(p);
				diccVehiculoProductos.put(c, l);
			}
			else {
				List<Producto> l = new ArrayList<Producto>();
				l.add(p);
				diccVehiculoProductos.put(c, l);
			}
		}
		return diccVehiculoProductos;
	}
	
	@GetMapping("/{nombre}")
	@PreAuthorize("hasRole('DEPENDIENTE') or hasRole('ENCARGADO')")
	public Map<Categoria,List<Producto>> findProductosByName(@PathVariable("nombre") String nombre){
//		List<Categoria> lc = lp.stream().map(x->x.getEstanteria().getCategoria()).distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

		List<Producto> lp = productoService.findAllProductosByName(nombre);
		Map<Categoria,List<Producto>> diccVehiculoProductos = new HashMap<Categoria, List<Producto>>();
		
		for (int i = 0; i < lp.size(); i++) {
			Categoria c = lp.get(i).getEstanteria().getCategoria(); //clave
			Producto p = lp.get(i); //valor
			
			if(diccVehiculoProductos.containsKey(c)) {
				List<Producto> l = diccVehiculoProductos.get(c);
				l.add(p);
				diccVehiculoProductos.put(c, l);
			}
			else {
				List<Producto> l = new ArrayList<Producto>();
				l.add(p);
				diccVehiculoProductos.put(c, l);
			}
		}
		
		return diccVehiculoProductos;
	}
	
	@PostMapping("/save")
	public Producto saveProducto(@RequestBody Producto newProducto) {
		Producto p = productoService.findById(newProducto.getId());
		p.setNombre(newProducto.getNombre());
		p.setDescripcion(newProducto.getDescripcion());
		p.setStock(newProducto.getStock());
		p.setStockSeguridad(newProducto.getStockSeguridad());
		p.setAncho(newProducto.getAncho());
		p.setAlto(newProducto.getAlto());
		p.setProfundo(newProducto.getProfundo());
		p.setPrecioVenta(newProducto.getPrecioVenta());
		p.setPrecioCompra(newProducto.getPrecioCompra());
		return productoService.save(p);
	}
	
	@ExceptionHandler({ Exception.class })
	@PostMapping("/add")
	public Producto saveNuevoProducto( @RequestBody Producto newProducto) {
	
		return productoService.save(newProducto);
	}
	
	@GetMapping("/demanda")
	public Double getDemanda() {
		Producto prod = productoService.findById(1L);
		Double res = productoService.getDemanda(prod, LocalDate.of(0, 1, 1));
		System.out.println("TOMAAS" + res);
		return res;
		
	}
	
	
	@GetMapping("/paged")
	public Page<Producto> getAllProductosPageable(Pageable pageable) {
		return productoService.findAllPageable(pageable);		
	}
	
	@GetMapping("/paged/{category}")
	public Page<Producto> getAllProductosCategoriaPageable(@PathVariable("category") String categoria,Pageable pageable) {
		return productoService.findByEstanteriaPageable(Categoria.valueOf(categoria), pageable);		
	}
	
	@GetMapping("/pagedName/{string}")
	public Page<Producto> getAllProductosNombrePageable(@PathVariable("string") String s,Pageable pageable) {
		return productoService.findByProductosByNamePageable(s, pageable);		
	}
	
	@GetMapping("/pagedOrd")
	public Page<Producto> getAllProductosOrderedPageable(Pageable pageable) {
		return productoService.findAllOrderedPageable(pageable);		
	}
	
	@GetMapping("/pagedOrd/{category}")
	public Page<Producto> getAllProductosCategoriaOrderedPageable(@PathVariable("category") String categoria,Pageable pageable) {

		return productoService.findByEstanteriaOrderedPageable(Categoria.valueOf(categoria), pageable);		
	}
	
	@GetMapping("/pagedNameOrd/{string}")
	public Page<Producto> getAllProductosNombreOrderedPageable(@PathVariable("string") String s,Pageable pageable) {
		return productoService.findByProductosByNameOrderedPageable(s, pageable);		
	}
	
	
	@ExceptionHandler({ Exception.class })
	@PreAuthorize("hasRole('ENCARGADO')")
	@PostMapping("/notiStock")
	public ResponseEntity<?> createNotiStock(@RequestBody Producto newProducto) {
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		EncargadoDeAlmacen encargado = (EncargadoDeAlmacen) userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		String cuerpo = "El Producto  "+newProducto.getNombre()+"-("+newProducto.getId()+") se recomienda su reposicion por bajo stock en el almacen " + encargado.getAlmacen().getId();
		
		List<Notificacion> notisprod = notificacionService.findNotiPersonalFechaCuerpo(encargado, LocalDate.now(),cuerpo);
		
		if(!notisprod.isEmpty()) {
			return ResponseEntity.badRequest().body("Ya ha mandado esta notificacion hoy");
		}else {
			Notificacion noti = new Notificacion();
			
			noti.setEmisor(encargado);
			noti.setCuerpo(cuerpo);
			noti.setTipoNotificacion(TipoNotificacion.URGENTE);
			noti.setFechaHora(LocalDateTime.now());
			
			List<Personal> adminReceptores = new ArrayList<>();
			for (Administrador adm : adminService.findAll()) {
				adminReceptores.add(adm);
			}
			notificacionService.save(noti, adminReceptores);
			
			return ResponseEntity.ok("Todo ok bro");
			
		}
	}
	
}
