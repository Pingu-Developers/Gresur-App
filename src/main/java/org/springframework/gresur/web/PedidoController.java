package org.springframework.gresur.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.service.PedidoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	

}
