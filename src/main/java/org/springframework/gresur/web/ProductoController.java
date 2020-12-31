package org.springframework.gresur.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.service.ProductoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/producto")
@RestController
public class ProductoController {
	
	private final ProductoService productoService;
	
	@Autowired
	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public Pair<List<Producto>, List<Categoria>> getCatalogo(){
		return Pair.of(productoService.findAll(), Arrays.asList(Categoria.values()));
	}
	
	

}
