package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class AlmacenServiceTests {
	
	@Autowired
	protected AlmacenService almacenService;

	@BeforeEach
	@Transactional
	void initAll() {
		Almacen alm = new Almacen();
		alm.setCapacidad(30.0);
		alm.setDireccion("Los Algodonales");

		almacenService.save(alm);
	}
	
	@AfterEach
	@Transactional
	void clearAll() {
		almacenService.deletAll();
	}
	
	//Tests
	
	@Test
	@Transactional
	void findAlmacenById() {
		Long id = almacenService.findAll().iterator().next().getId();
		Almacen alm = almacenService.findById(id);
		assertThat(alm.getDireccion()).isEqualTo("Los Algodonales");
	}
	
	@Test
	@Transactional	
	void deleteAlmacenById() {
		Long id = almacenService.findAll().iterator().next().getId();
		almacenService.deleteById(id);
		assertThat(almacenService.count()).isEqualTo(0);
	}

}
