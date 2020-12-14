package org.springframework.gresur.services;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Concepto;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
public class FacturaRecibidaServiceTests {

	@Autowired
	protected DBUtility util;
	
	@Autowired
	protected FacturaRecibidaService facturaRecibidaService;
	
	@Autowired
	protected ProveedorService proveedorService;

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

		Proveedor proveedor = new Proveedor();
		proveedor.setName("Hierros Manuel Ximenez");
		proveedor.setDireccion("Calle Los Pajaros Azules Nº10");
		proveedor.setEmail("hierrosXimen@gmail.com");
		proveedor.setTlf("985453120");
		proveedor.setIBAN("ES6621000418401234567891");
		proveedor.setNIF("10070245R");
		proveedorService.save(proveedor);
		
		Proveedor proveedor2 = new Proveedor();
		proveedor2.setName("Cementos Manolo Garcia");
		proveedor2.setDireccion("Calle Los Vientos Azules Nº10");
		proveedor2.setEmail("mangar@gmail.com");
		proveedor2.setTlf("685453120");
		proveedor2.setIBAN("ES5621000418401234567891");
		proveedor2.setNIF("20070245J");
		proveedorService.save(proveedor2);
		
		FacturaRecibida fr = new FacturaRecibida();
		fr.setConcepto(Concepto.REPOSICION_STOCK);
		fr.setEstaPagada(true);
		fr.setFecha(LocalDate.of(2020, 3, 14));
		fr.setImporte(580.5);
		fr.setProveedor(proveedor);
		
		FacturaRecibida fr2 = new FacturaRecibida();
		fr2.setConcepto(Concepto.REPOSICION_STOCK);
		fr2.setEstaPagada(true);
		fr2.setFecha(LocalDate.of(2020, 5, 17));
		fr2.setImporte(185.);
		fr2.setProveedor(proveedor2);
		
		FacturaRecibida fr3 = new FacturaRecibida();
		fr3.setConcepto(Concepto.OTROS);
		fr3.setDescripcion("Repostaje");
		fr3.setEstaPagada(true);
		fr3.setFecha(LocalDate.of(2020, 10, 21));
		fr3.setImporte(53.);
		
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	* 										FUNCIONES DE LOS TESTS													 *
	* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */

	@Test
	@Transactional
	void findAll() {
		facturaRecibidaService.findAll();
	}


}