package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.transaction.annotation.Transactional;

class EstanteriaServiceTests {

	@Autowired
	protected EstanteriaService estanteriaService;
	
	@Autowired
	protected AlmacenService almacenService;
	
	@BeforeEach
	@Transactional
	void initAll() {
		Almacen alm = new Almacen();
		alm.setCapacidad(30.0);
		alm.setDireccion("Los Algodonales");

		almacenService.save(alm);
		
		Estanteria est = new Estanteria();
		est.setAlmacen(alm);
		est.setCapacidad(3000.0);
		est.setCategoria(Categoria.CEMENTOS);
		est.setProductos(null);
		
		estanteriaService.save(est);
	}
	@AfterEach
	@Transactional
	void clearAll() {
		almacenService.deletAll();
		estanteriaService.deleteAll();
	}
	
	//Tests
//	
//	@CsvSource({
//		"3000"
//	})
//	@ParameterizedTest
//	@Transactional
//	void findEstanteriaById(Double capacidad) {
//		Long id = estanteriaService.findAll().iterator().next().getId();
//		Estanteria est = estanteriaService.findById(id);
//		assertThat(est.getCapacidad()).isEqualTo(capacidad);
//	} 
//	
//	@CsvSource({
//		"Los Algodonales"
//	})
//	@ParameterizedTest
//	@Transactional
//	void deleteEstanteriaById() {
//		Long id = estanteriaService.findAll().iterator().next().getId();
//		estanteriaService.deleteById(id);
//		assertThat(estanteriaService.count()).isEqualTo(0);
//	}
//	
//	@Transactional
//	void findAllEstanteriaByAlmacenTest() {
//		Almacen alm = almacenService.findAll().iterator().next();
//		for(int i=0; i<100; i++) {
//			Estanteria est = new Estanteria();
//			est.setAlmacen(alm);
//			est.setCapacidad(3000.0);
//			est.setCategoria(Categoria.CEMENTOS);
//			est.setProductos(null);
//			
//			estanteriaService.save(est);
//		}
//		Integer numEstanterias = estanteriaService.findAllEstanteriaByAlmacen(alm.getId()).size();
//		assertThat(numEstanterias).isEqualTo(101);
//		
//	}
}
