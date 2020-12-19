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
	@DisplayName("Buscar todas las Estanterias en un almacen -- caso positivo")
	void listAllEstanteriasInAlmacen() {
		Long idAlmacen = almacenService.findAll().iterator().next().getId();
		List<Estanteria> ls = estanteriaService.findAllEstanteriaByAlmacen(idAlmacen);
		assertThat(ls.size()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	@DisplayName("Buscar todas las Estanterias en un almacen -- caso negativo")
	void listAllEstanteriasInAlmacenNotFound() {
		Long idAlmacen = 200L;
		List<Estanteria> ls = estanteriaService.findAllEstanteriaByAlmacen(idAlmacen);
		assertThat(ls).isEmpty();
	}
	
	@Test
	@Transactional
	@DisplayName("Suma de capacidad de estanterias exceptuando una concreta -- caso positivo")
	void sumCapacidadEstanteriasAlmacenNotEqualTo() {
		Almacen almacen = almacenService.findAll().iterator().next();
		Estanteria estanteria = estanteriaService.findAllEstanteriaByAlmacen(almacen.getId()).get(0);
		Double sumCapacidad = estanteriaService.sumCapacidadEstanteriasAlmacenNotEstanteria(almacen, estanteria.getId());
		assertThat(sumCapacidad).isEqualTo(6000.);
	}
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	@Test
	@Transactional
	@DisplayName("RN: Capacidad Estanteria menor que capacidad Almacen (new) -- caso positivo")
	void saveEstanteriaWithCapacidadExcededPositive() {
		
		Almacen alm = almacenService.findAll().iterator().next();
		Estanteria est = new Estanteria();
		est.setAlmacen(alm);
		est.setCapacidad(160.00);
		est.setCategoria(Categoria.PINTURAS);
		est = estanteriaService.save(est);
		
		assertThat(estanteriaService.findById(est.getId())).isEqualTo(est);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Capacidad Estanteria menor que capacidad Almacen (update) -- caso positivo")
	void updateEstanteriaWithCapacidadExcededPositive() {
		
		Estanteria est = estanteriaService.findAll().iterator().next();
		est.setCapacidad(159.00);
		assertThat(estanteriaService.findAll().iterator().next().getCapacidad()).isEqualTo(159.00);
	}
	
	
	@Test
	@Transactional
	@DisplayName("RN: Capacidad Estanteria menor que capacidad Almacen (new) -- caso negativo")
	void saveEstanteriaWithCapacidadExcededNegative() {
		
		Almacen alm = almacenService.findAll().iterator().next();
		Estanteria est = new Estanteria();
		est.setAlmacen(alm);
		est.setCapacidad(1000000.00);
		est.setCategoria(Categoria.PINTURAS);
		
		assertThrows(CapacidadEstanteriaExcededException.class, () -> {estanteriaService.save(est);});
		List<Estanteria> le = estanteriaService.findAllEstanteriaByAlmacen(alm.getId());
		assertThat(le.get(le.size()-1)).isNotEqualTo(est);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Capacidad Estanteria menor que capacidad Almacen (update) -- caso negativo")
	void updateEstanteriaWithCapacidadExcededNegative() {
		
		Estanteria est = estanteriaService.findAll().iterator().next();
		est.setCapacidad(1000000.00);
		
		assertThrows(CapacidadEstanteriaExcededException.class, () -> {estanteriaService.save(est);});
	}
}
