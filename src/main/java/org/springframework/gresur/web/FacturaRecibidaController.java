package org.springframework.gresur.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/facturaRecibida")
@Slf4j
public class FacturaRecibidaController {

	private final FacturaRecibidaService facturaRecibidaService;
	
	private final ProductoService productoService;
	
	private final LineasFacturaService lineaFacturaService;
	
	private final SeguroService seguroService;
	
	private final ITVService itvService;
	
	private final ReparacionService reparacionService;

	
	@Autowired
	public FacturaRecibidaController(FacturaRecibidaService facturaRecibidaService, ProductoService productoService , LineasFacturaService lineaFacturaService
			,SeguroService seguroService, ITVService itvService, ReparacionService reparacionService) {
		this.facturaRecibidaService= facturaRecibidaService;
		this.productoService = productoService;
		this.lineaFacturaService = lineaFacturaService;
		this.seguroService= seguroService;
		this.itvService = itvService;
		this.reparacionService = reparacionService;
	}
	
	@Transactional
	@PostMapping("/repo")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createEmitidaReposicion(@Valid @RequestBody Tuple2<FacturaRecibida,List<Tuple2<Long,Integer>>> data, BindingResult result){
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/facturaRecibida/repo Constrain validation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		try {
			FacturaRecibida fact = data.getE1();
			fact.setFechaEmision(LocalDate.now());
			fact = facturaRecibidaService.save(fact);
			
			List<LineaFactura> lineasCompra = new ArrayList<>();
			for (Tuple2<Long,Integer> linea : data.getE2()) {
				Producto p = productoService.findById(linea.getE1());
				
				LineaFactura lf = new LineaFactura();
				lf.setCantidad(linea.getE2());
				lf.setProducto(p);
				lf.setPrecio(p.getPrecioCompra()*lf.getCantidad());
				lf.setFactura(fact);
				fact.setImporte(fact.getImporte()+lf.getPrecio());
				
				lf = lineaFacturaService.save(lf);
				
				p.setStock(p.getStock()+lf.getCantidad());
				productoService.save(p);
				lineasCompra.add(lf);
			}
			
			fact.setLineasFacturas(lineasCompra);
			fact = facturaRecibidaService.save(fact);
			log.info("/facturaRecibida/repo Entity FacturaRecibida with id: "+fact.getId()+" was created successfully");
			return ResponseEntity.ok(fact);
		}catch(Exception e) {
			log.error("/facturaRecibida/repo "+e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Transactional
	@PostMapping("/seguro")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createRecibidaSeguro(@Valid @RequestBody Tuple2<FacturaRecibida,Seguro> data, BindingResult result){
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/facturaRecibida/seguro Constrain validation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		try {
			FacturaRecibida fact = data.getE1();
			fact.setFechaEmision(LocalDate.now());
			fact = facturaRecibidaService.save(fact);
			
			Seguro s = data.getE2();
			s.setRecibidas(fact);
			seguroService.save(s);
			log.info("/facturaRecibida/seguro Entity FacturaRecibida with id: "+fact.getId()+" was created successfully");
			return ResponseEntity.ok(fact);
		}catch(Exception e) {
			log.error("/facturaRecibida/seguro "+e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Transactional
	@PostMapping("/itv")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createRecibidaITV(@Valid @RequestBody Tuple2<FacturaRecibida,ITV> data, BindingResult result){
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/facturaRecibida/itv Constrain validation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		try {
			FacturaRecibida fact = data.getE1();
			fact.setFechaEmision(LocalDate.now());
			fact = facturaRecibidaService.save(fact);
			
			ITV i = data.getE2();
			
			i.setRecibidas(fact);
			itvService.save(i);
			log.info("/facturaRecibida/itv Entity FacturaRecibida with id: "+fact.getId()+" was created successfully");
			return ResponseEntity.ok(fact);
		}catch(Exception e) {
			log.error("/facturaRecibida/itv "+e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Transactional
	@PostMapping("/reparacion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createRecibidaReparacion(@Valid @RequestBody Tuple2<FacturaRecibida,Reparacion> data, BindingResult result){
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/facturaRecibida/reparacion Constrain validation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		try {
			FacturaRecibida fact = data.getE1();
			fact.setFechaEmision(LocalDate.now());
			fact = facturaRecibidaService.save(fact);
			
			Reparacion r = data.getE2();
			
			r.setRecibidas(fact);
			reparacionService.save(r);
			log.info("/facturaRecibida/reparacion Entity FacturaRecibida with id: "+fact.getId()+" was created successfully");
			return ResponseEntity.ok(fact);
		}catch(Exception e) {
			log.error("/facturaRecibida/reparacion "+e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Transactional
	@PostMapping("/otro")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createRecibidaOtro(@Valid @RequestBody FacturaRecibida data, BindingResult result){
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			log.warn("/facturaRecibida/otro Constrain validation in params");
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		try {
			FacturaRecibida fact = data;
			fact.setFechaEmision(LocalDate.now());
			fact = facturaRecibidaService.save(fact);
			log.info("/facturaRecibida/otro Entity FacturaRecibida with id: "+fact.getId()+" was created successfully");
			return ResponseEntity.ok(fact);
		}catch(Exception e) {
			log.error("/facturaRecibida/otro "+e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}