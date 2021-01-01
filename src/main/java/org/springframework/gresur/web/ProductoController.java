package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.service.ProductoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public Pair<List<Producto>, List<Categoria>> findProductos(){
		return Pair.of(productoService.findAll(), Arrays.asList(Categoria.values()));
	}
	
	@GetMapping("/{nombre}")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public Pair<List<Producto>, List<Categoria>> findProductosByName(@PathVariable("nombre") String nombre){
		
		List<Producto> lp = productoService.findAllProductosByName(nombre);
		
		List<Categoria> lc = new ArrayList<Categoria>();
		lp.stream().map(x->x.getEstanteria().getCategoria()).distinct().forEach(x->lc.add(x));
		Collections.sort(lc);
		
		return Pair.of(lp, lc);
	}
	

}
