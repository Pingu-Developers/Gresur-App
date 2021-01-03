package org.springframework.gresur.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/pedido")
@RestController
public class PedidoController {
	
	private final PedidoService pedidoService;

	@Autowired
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@Autowired
	public VehiculoService vehiculoService;
	
	@GetMapping
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public Iterable<Pedido> findAll() {
		return pedidoService.findAll();
	}
	
	@GetMapping("/{estado}")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public List<Pedido> findAllByEstado(@PathVariable("estado") String estado) {
		return pedidoService.findByEstado(EstadoPedido.valueOf(estado));
	}
	
	@PostMapping("/{id}")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public ResponseEntity<?> cancelarPedido(@PathVariable("id") Long id) {
		
		Pedido pedido = pedidoService.findByID(id);
		
		if(pedido != null) {
			pedido.setEstado(EstadoPedido.CANCELADO);
			try {
				pedidoService.save(pedido);
				return ResponseEntity.ok(pedido);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e);
			}
		}
		else {
			return ResponseEntity.badRequest().body("Error: Pedido not found");
		}
	}
	
	@GetMapping("/ocupacion")
	@PreAuthorize("hasRole('TRANSPORTISTA')")
	public List<Tuple3<Vehiculo, Double, Double>> ocupacionPedidosDelDia() {
		
		Iterable<Vehiculo> iterableVehiculos = vehiculoService.findAll();
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		iterableVehiculos.forEach(listaVehiculos::add);
		
		List<Vehiculo> vehiculosFiltrado = listaVehiculos.stream()
				.filter(x->x.getTipoVehiculo()==TipoVehiculo.CAMION || x.getTipoVehiculo()==TipoVehiculo.FURGONETA).collect(Collectors.toList());
		
		
		List<Pedido> lp = pedidoService.findPedidosEnRepartoByFecha(LocalDate.now());
		
		Map<Vehiculo,List<FacturaEmitida>> diccVehiculoDimPedidos = new HashMap<Vehiculo, List<FacturaEmitida>>();
		
		for (int i = 0; i < lp.size(); i++) {
			Vehiculo v = lp.get(i).getVehiculo(); //clave
			FacturaEmitida f = lp.get(i).getFacturaEmitida(); //valor
			
			if(diccVehiculoDimPedidos.containsKey(v)) {
				List<FacturaEmitida> lf = diccVehiculoDimPedidos.get(v);
				lf.add(f);
				diccVehiculoDimPedidos.put(v, lf);
			}
			else {
				List<FacturaEmitida> l = new ArrayList<FacturaEmitida>();
				l.add(f);
				diccVehiculoDimPedidos.put(v, l);
			}
		}
		
		
		List<Tuple3<Vehiculo, Double, Double>> listaDef = new ArrayList<Tuple3<Vehiculo,Double,Double>>();
		
		for (Vehiculo v : vehiculosFiltrado) {
			
			if(diccVehiculoDimPedidos.get(v)!=null) {
				Tuple3<Vehiculo, Double, Double> tp = new Tuple3<Vehiculo, Double, Double>();
				tp.setE1(v);
				
				
				List<LineaFactura> l = diccVehiculoDimPedidos.get(v).stream().map(x->x.getLineasFacturas()).flatMap(List::stream).collect(Collectors.toList());
				
				
				Double dimProductos = l.stream().mapToDouble(x->x.getProducto().getAlto()*x.getProducto().getAncho()*x.getProducto().getProfundo()).sum();
				tp.setE2(dimProductos);
				
				
				Double dimTotal = v.getCapacidad();
				tp.setE3(dimTotal);
				
				tp.name1="vehiculo";
				tp.name2="dimProductos";
				tp.name3="dimTotales";
				
				listaDef.add(tp);
			}
			
		}
	
		return listaDef;
	}
	
}
