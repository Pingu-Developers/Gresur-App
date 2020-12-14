package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.exceptions.CapacidadEstanteriaExcededException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class EstanteriaServiceTests {

	@Autowired
	protected EstanteriaService estanteriaService;
	
	@Autowired
	protected AlmacenService almacenService;
	
	@Autowired
	protected DBUtility util;
	
	
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 										FUNCIONES DE CARGA DE DATOS PARA LOS TESTS								 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@BeforeAll
	@AfterEach
	@Transactional
	void clearDB() {
		util.clearDB();
	}
	
	@BeforeEach
	@Transactional
	void initAll() {
		Almacen alm = new Almacen();
		alm.setCapacidad(30000.0);
		alm.setDireccion("Los Algodonales");
		almacenService.save(alm);
		
		Estanteria est = new Estanteria();
		est.setAlmacen(alm);
		est.setCapacidad(3000.0);
		est.setCategoria(Categoria.SILICES);
		
		estanteriaService.save(est);
		
		Estanteria est1 = new Estanteria();
		est1.setAlmacen(alm);
		est1.setCapacidad(3000.0);
		est1.setCategoria(Categoria.AZULEJOS);
		
		estanteriaService.save(est1);
		
		Estanteria est2 = new Estanteria();
		est2.setAlmacen(alm);
		est2.setCapacidad(3000.0);
		est2.setCategoria(Categoria.LADRILLOS);
		
		estanteriaService.save(est2);		
	}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */

	@Test
	@Transactional
	void listAllEstanteriasInAlmacen() {
		Long idAlmacen = almacenService.findAll().iterator().next().getId();
		List<Estanteria> ls = estanteriaService.findAllEstanteriaByAlmacen(idAlmacen);
		assertThat(ls.size()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	void listAllEstanteriasInAlmacenNotFound() {
		Long idAlmacen = 200L;
		List<Estanteria> ls = estanteriaService.findAllEstanteriaByAlmacen(idAlmacen);
		assertThat(ls).isEmpty();
	}
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("RN: Capacidad Estanteria menor que capacidad Almacen")
	void saveEstanteriaWithCapacidadExceded() {
		Almacen alm = almacenService.findAll().iterator().next();
		Estanteria est = new Estanteria();
		est.setAlmacen(alm);
		est.setCapacidad(1000000.00);
		est.setCategoria(Categoria.PINTURAS);
		assertThrows(CapacidadEstanteriaExcededException.class, () -> {estanteriaService.save(est);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Capacidad Estanteria menor que capacidad Almacen")
	void updateEstanteriaWithCapacidadExceded() {
		Estanteria est = estanteriaService.findAll().iterator().next();
		est.setCapacidad(1000000.00);
		assertThrows(CapacidadEstanteriaExcededException.class, () -> {estanteriaService.save(est);});
	}
}
