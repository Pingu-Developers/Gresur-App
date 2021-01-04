package org.springframework.gresur.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple3;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RequestMapping("api/almacen")
@RestController
public class AlmacenController {
	
	private AlmacenService almacenService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private EstanteriaService zonaService;
	
	@Autowired
	public AlmacenController(AlmacenService almacenService) {
		this.almacenService = almacenService;
	}
	
	
	@GetMapping("/gestion")
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

	
	private List<Tuple2<Categoria, Double>> getCategoriasCapacidad(Almacen alm) {
		List<Tuple2<Categoria, Double>> res = new ArrayList<>();
		List<Categoria> categorias = Arrays.asList(Categoria.values());
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


	private Double getOcupacionTotalAlmacen(Almacen alm) {
		Double capacidadTotal = alm.getCapacidad();
		Double productosOcupacion = productoService.findAll().stream()
		.filter(x -> x.getEstanteria().getAlmacen().equals(alm))
		.map(x -> x.getAlto() * x.getAncho() * x.getProfundo() * x.getStock())
		.mapToDouble(x -> x).sum();
		
		return productosOcupacion/capacidadTotal;
	}

}