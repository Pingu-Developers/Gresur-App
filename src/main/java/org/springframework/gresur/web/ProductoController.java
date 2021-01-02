package org.springframework.gresur.web;

import java.util.Arrays;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.RequestBody;
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
	@PreAuthorize("hasRole('DEPENDIENTE') or hasRole('ENCARGADO')")
	public Pair<List<Producto>, List<Categoria>> findProductos(){
		return Pair.of(productoService.findAll(), Arrays.asList(Categoria.values()));
	}
	
	@GetMapping("/{nombre}")
	@PreAuthorize("hasRole('DEPENDIENTE') or hasRole('ENCARGADO')")
	public Pair<List<Producto>, List<Categoria>> findProductosByName(@PathVariable("nombre") String nombre){
		
		List<Producto> lp = productoService.findAllProductosByName(nombre);
		
		List<Categoria> lc = lp.stream().map(x->x.getEstanteria().getCategoria()).distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
		
		return Pair.of(lp, lc);
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
	

}
