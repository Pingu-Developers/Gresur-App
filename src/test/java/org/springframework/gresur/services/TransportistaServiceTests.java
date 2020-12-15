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
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class TransportistaServiceTests {
	
	@Autowired
	protected TransportistaService transportistaService;
	
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
		
		// CREACION DEL TRANSPORTISTA
		Transportista transportista = new Transportista();
		transportista.setName("Jose Luis Gresur");
		transportista.setNIF("18845878A");
		transportista.setEmail("e1@email.com");
		transportista.setTlf("696823445");
		transportista.setDireccion("Av. Reina Mercedes");
		transportista.setNSS("12 1234123890");
		transportista.setImage("/resources/foto.png");
		
		transportistaService.save(transportista);
	}
	
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Buscar Transportista por su NIF -- caso positivo")
	void findTransportistaByNif() {
		Transportista transportista = transportistaService.findByNIF("18845878A");
		assertThat(transportista.getName()).isEqualTo("Jose Luis Gresur");
	}
	
	@Test
	@Transactional
	@DisplayName("Buscar Transportista por su NIF -- caso negativo")
	void findTransportistaByNifNotFound() {
		Transportista transportista = transportistaService.findByNIF("18845878C");
		assertThat(transportista).isEqualTo(null);
	}
	
	@Test
	@Transactional	
	@DisplayName("Borrar Transportista por su NIF -- caso positivo")
	void deleteTransportistaByNIF() {
		transportistaService.deleteByNIF("18845878A");
		assertThat(transportistaService.count()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Buscar Transportista por su NIF -- caso negativo")
	void deleteTransportistaByNIFNotFound() {
		transportistaService.deleteByNIF("18845878S");
		assertThat(transportistaService.count()).isEqualTo(1);
	}

}