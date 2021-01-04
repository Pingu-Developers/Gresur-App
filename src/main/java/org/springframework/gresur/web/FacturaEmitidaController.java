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
import org.springframework.gresur.model.userPayload.MessageResponse;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	public List<FacturaEmitida> getFacturasCliente(@PathVariable("id") Long id){
		
		List<FacturaEmitida> values = facturaEmitidaService.findFacturasByCliente(id);
		
		return values.stream().filter(x -> x.esDefinitiva()).collect(Collectors.toList());
	}
	
	@PostMapping("/clienteFecha")
	public List<FacturaEmitida> getFacturasClienteAndFecha(@Valid @RequestBody Tuple2<Long, LocalDate> data){
		
		List<FacturaEmitida> values = facturaEmitidaService.findFacturasByClienteAndFecha(data.getE1(), data.getE2());
		
		return values.stream().filter(x -> x.esDefinitiva()).collect(Collectors.toList());
	}
	
	
	@PostMapping("/devolucion")
	public ResponseEntity<?> createDevolucion(@Valid @RequestBody Tuple3<FacturaEmitida,String,List<Tuple2<Long,Integer>>> data){
		
		
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
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
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Has devuelto mas productos de lo que tienes!"));
					}	
				}
			}			

			LineaFactura lf = new LineaFactura();
			lf.setProducto(linea.getProducto());
			lf.setCantidad(diff);
			lf.setPrecio(linea.getPrecio()/linea.getCantidad()*lf.getCantidad());
			importe += lf.getPrecio();
			lf.setFactura(devolucion);
			
			lineasDevolucion.add(lf);
		}
		
		lineaFacturaService.saveAll(lineasDevolucion);
		devolucion.setLineasFacturas(lineasDevolucion);
		devolucion.setImporte(importe);
		
		devolucion = facturaEmitidaService.save(devolucion);
		
		return ResponseEntity.ok(devolucion);
	}
	
	

}
