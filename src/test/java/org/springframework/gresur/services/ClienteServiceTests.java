package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class ClienteServiceTests {
	
	@Autowired
	protected ClienteService clienteService;
	
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
		Cliente cliente = new Cliente();
		cliente.setDireccion("Calle");
		cliente.setEmail("cliente@mail.es");
		cliente.setName("Jose Luis");
		cliente.setNIF("54789663T");
		cliente.setTlf("657412397");
		
		clienteService.save(cliente);
	}
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	void findClienteByNIF() {
		Cliente cliente = clienteService.findByNIF("54789663T");
		assertThat(cliente.getName()).isEqualTo("Jose Luis");
	}

	@Test
	@Transactional
	void findClienteByNifNotFound() {
		Cliente cliente = clienteService.findByNIF("54789662K");
		assertThat(cliente).isEqualTo(null);
	}
	
	@Test
	@Transactional
	void deleteClienteByNIF() {
		clienteService.deleteByNIF("54789663T");
		assertThat(clienteService.count()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	void deleteClienteByNIFNotFound() {
		clienteService.deleteByNIF("54789669J");
		assertThat(clienteService.count()).isEqualTo(1);
	}
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
}
