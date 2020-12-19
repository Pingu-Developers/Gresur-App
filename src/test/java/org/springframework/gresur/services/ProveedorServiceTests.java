package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
public class ProveedorServiceTests {

	@Autowired
	protected ProveedorService proveedorService;
	
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
	void InitAll() {

		// CREACION DE PROVEEDOR
		
		Proveedor proveedor = new Proveedor();
		proveedor.setName("Hierros Hermanos Sainz");
		proveedor.setDireccion("Calle Reina Leonor Nº17");
		proveedor.setEmail("hierrossainz@gmail.com");
		proveedor.setTlf("987582210");
		proveedor.setNIF("10030284R");
		proveedor.setIBAN("ES6621000418401234567891");
		proveedor = proveedorService.save(proveedor);
		
		Proveedor proveedor2 = new Proveedor();
		proveedor2.setName("Cementos Manolo Garcia");
		proveedor2.setDireccion("Calle Los Vientos Azules Nº10");
		proveedor2.setEmail("mangar@gmail.com");
		proveedor2.setTlf("685453120");
		proveedor2.setIBAN("ES5621000418401234567891");
		proveedor2.setNIF("20070245J");
		proveedorService.save(proveedor2);
		
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	* 										FUNCIONES DE LOS TESTS													 *
	* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */

	@Test
	@Transactional
	@DisplayName("Encontrar proveedor dado su NIF -- caso positivo")
	void findByNif() {
		Proveedor proveedor = proveedorService.findByNIF("10030284R");
		Proveedor proveedor2 = proveedorService.findByNIF("20070245J");
		assertThat(proveedor.getName()).isEqualTo("Hierros Hermanos Sainz");
		assertThat(proveedor2.getName()).isEqualTo("Cementos Manolo Garcia");


	}
	
	@Test
	@Transactional
	@DisplayName("Encontrar proveedor dado su NIF -- caso negativo")
	void findByNifNotFound() {
		Proveedor proveedor = proveedorService.findByNIF("20070245Y");
		assertThat(proveedor).isEqualTo(null);

	}



}