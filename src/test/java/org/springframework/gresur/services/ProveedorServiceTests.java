package org.springframework.gresur.services;

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
		//TODO CARGA DE DATOS EN ESTE ORDEN:

		// CREACION DE ALMACEN

		// CREACION DE ESTANTERIA

		// CREACION DE PRODUCTO

		// CREACION DE CLIENTE

		// CREACION DE PROVEEDOR

		// CREACION DE ADMINISTRADOR

		// CREACION DE ENCARGADO

		// CREACION DE DEPENDIENTE

		// CREACION DE TRANSPORTISTA

		// CREACION DE CONTRATO

		// CREACION DE NOTIFICACION

		// CREACION DE LINEA ENVIO

		// CREACION DE FACTURA EMITIDA

		// CREACION DE FACTURA RECIBIDA

		// CREACION DE LINEA FACTURA

		// CREACION DE PEDIDO

		// CREACION DE VEHICULO

		// CREACION DE REPARACION

		// CREACION DE ITV

		// CREACION DE SEGURO

		// CREACION DE CONFIGURACION

	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	* 										FUNCIONES DE LOS TESTS													 *
	* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */

	@Test
	@Transactional
	void FindRemoveTest() {
		//TODO test
	}

	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */

	@Test
	@Transactional
	@DisplayName("")
	void RNTest() {
		//TODO RN test
	}
}