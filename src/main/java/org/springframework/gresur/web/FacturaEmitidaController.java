package org.springframework.gresur.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/facturaEmitida")
public class FacturaEmitidaController {
	
	private final FacturaEmitidaService facturaEmitidaService;
	
	private final ProductoService productoService;
	
	private final LineasFacturaService lineaFacturaService;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	public FacturaEmitidaController(FacturaEmitidaService facturaEmitidaService, ProductoService productoService , LineasFacturaService lineaFacturaService) {
		this.facturaEmitidaService = facturaEmitidaService;
		this.productoService = productoService;
		this.lineaFacturaService = lineaFacturaService;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DEPENDIENTE')")
	public ResponseEntity<?> getFacturasCliente(@PathVariable("id") Long id){
		
		List<FacturaEmitida> values = facturaEmitidaService.findFacturasByCliente(id);
		
		if(values==null) {
			return ResponseEntity.badRequest().body("No se encontraron factura para dicho cliente");
		}
		
		else {
			List<FacturaEmitida> lfe = values.stream().filter(x -> x.esDefinitiva()).collect(Collectors.toList());
			return ResponseEntity.ok(lfe);
		}
	}
	
	@PostMapping("/clienteFecha") //TODO revisar no es un post es un Get?
	@PreAuthorize("hasRole('ADMIN') or hasRole('DEPENDIENTE')")
	public ResponseEntity<?> getFacturasClienteAndFecha(@RequestBody Tuple2<Long, LocalDate> data){
		
		List<FacturaEmitida> values = facturaEmitidaService.findFacturasByClienteAndFecha(data.getE1(), data.getE2());
		
		if(values==null) {
			return ResponseEntity.badRequest().body("No se encontraron factura para dicho cliente");
		}
		
		else {
			List<FacturaEmitida> lfe = values.stream().filter(x -> x.esDefinitiva()).collect(Collectors.toList());
			return ResponseEntity.ok(lfe);
		}
	}
	
	@Transactional
	@ExceptionHandler({ Exception.class })
	@PostMapping("/devolucion")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DEPENDIENTE')")
	public ResponseEntity<?> createDevolucion(@Valid @RequestBody Tuple3<FacturaEmitida,String,List<Tuple2<Long,Integer>>> data, BindingResult result){
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		if(data.getE3().size() == 0) {
			throw new IllegalArgumentException("Factura sin lineas");
		}
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		
		
		try {
			FacturaEmitida original = facturaEmitidaService.findById(data.getE1().getId());
			FacturaEmitida devolucion = new FacturaEmitida();
			
			
			List<LineaFactura> lineasDevolucion = new ArrayList<>();
			
			Double importe = 0.;
							
			devolucion.setDependiente((Dependiente) per);
			devolucion.setCliente(original.getCliente());
			devolucion.setDescripcion(data.getE2());
			devolucion.setEstaPagada(true);
			devolucion.setImporte(importe);		
			devolucion.setOriginal(original);
			
			devolucion = facturaEmitidaService.save(devolucion);
			
			
			for (LineaFactura linea : original.getLineasFacturas()) {
				
				Integer diff = linea.getCantidad() ;
				
				for (Tuple2<Long,Integer> pareja : data.getE3()) {	
					
					Producto prod = productoService.findById(pareja.getE1());
					Producto prod2 = productoService.findById(linea.getProducto().getId());
					if(prod2==prod) {
						diff = linea.getCantidad()-pareja.getE2();
						if(diff<0) {
							throw new IllegalArgumentException("Cantidad superada");
						}
						prod.setStock(prod.getStock()+pareja.getE2());
						productoService.save(prod);
					}
				}			

				LineaFactura lf = new LineaFactura();
				lf.setProducto(linea.getProducto());
				lf.setCantidad(diff);
				Double precioNew = linea.getCantidad()!=0?linea.getPrecio()/linea.getCantidad()*lf.getCantidad():0;
				lf.setPrecio(precioNew);
				importe += lf.getPrecio();
				lf.setFactura(devolucion);
				
				lineasDevolucion.add(lf);
			}
			
			lineasDevolucion = lineaFacturaService.saveAll(lineasDevolucion);
			
			devolucion.setLineasFacturas(lineasDevolucion);
			devolucion.setImporte(importe);
			devolucion = facturaEmitidaService.save(devolucion);
			
			return ResponseEntity.ok(devolucion);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}	
	}
	
	@Transactional
	@GetMapping("/cargar/{numFactura}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DEPENDIENTE')")
	public ResponseEntity<?> getFacturaByNumFactura(@PathVariable String numFactura) {
		FacturaEmitida factura = facturaEmitidaService.findByNumFactura(numFactura);
		if(factura != null) {
			if(factura.esDefinitiva()) {
				return ResponseEntity.ok(factura);
			}else {
				return ResponseEntity.badRequest().body("Esta factura esta rectificada compruebe: "+ factura.getDefinitiva().getNumFactura());
			}
		} else {
			return ResponseEntity.badRequest().body("No se ha encontrado la factura");
		}
	}
	
	@Transactional
	@PostMapping("/rectificar")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> rectificarFactura(@Valid @RequestBody FacturaEmitida fra, BindingResult result){
		
		FacturaEmitida ori = facturaEmitidaService.findByNumFactura(fra.getOriginal().getNumFactura());
		
		if(ori==null) {
			return ResponseEntity.badRequest().body("Factura no encontrada en el sistema");
		}
		
		if(ori.getVersion()!=fra.getVersion()) {
			return ResponseEntity.badRequest().body("Concurrent modification");
		}
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		if(!fra.getOriginal().esDefinitiva()) {
			return ResponseEntity.badRequest().body("Intento modifiar una factura no final");
		}
		try {
			FacturaEmitida f = new FacturaEmitida();
			f.setCliente(fra.getCliente());
			f.setDependiente(fra.getDependiente());	
			f.setDescripcion(fra.getDescripcion());
			f.setEstaPagada(fra.getEstaPagada());
			f.setImporte(0.0);
			f.setOriginal(ori);
			FacturaEmitida f2 = facturaEmitidaService.save(f);
			
			List<LineaFactura> ls = fra.getLineasFacturas();
			ls.forEach(x -> x.setFactura(f2));
			for (LineaFactura lineaOriginal : f.getOriginal().getLineasFacturas()) {
				
				Integer diff = lineaOriginal.getCantidad() ;
				Producto prod = productoService.findById(lineaOriginal.getProducto().getId());
				
				for (LineaFactura newlinea : ls) {	
					
					Producto prod2 = productoService.findById(newlinea.getProducto().getId());	
					if(prod2==prod) {
						diff = lineaOriginal.getCantidad()-newlinea.getCantidad();				
					}
				}
				
				prod.setStock(prod.getStock()+diff);
				productoService.save(prod);	
			}
			
			List<LineaFactura> lsFinal = lineaFacturaService.saveAll(ls);
			
			f2.setLineasFacturas(lsFinal);
			f2.setImporte(fra.getImporte());
			FacturaEmitida f3 = facturaEmitidaService.save(f2);
			return ResponseEntity.ok(f3);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}


	}

}