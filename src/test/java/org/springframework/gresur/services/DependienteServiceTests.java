package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.service.DependienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class DependienteServiceTests {
	
	@Autowired
	protected DependienteService dependienteService;

	@BeforeEach
	@Transactional
	void initAll() {
		Dependiente dependiente = new Dependiente();
		dependiente.setName("Jose Luis Gresur");
		dependiente.setNIF("18845878A");
		dependiente.setEmail("e1@email.com");
		dependiente.setTlf("696823445");
		dependiente.setDireccion("Av. Reina Mercedes");
		dependiente.setNSS("12 1234123890");
		dependiente.setImage("/resources/foto.png");
		
		dependienteService.save(dependiente);
	}
	@AfterEach
	@Transactional
	void clearAll() {
		dependienteService.deleteAll();
	}
	
	//Tests
	
	@CsvSource({
		"18845878A, Jose Luis Gresur"
	})
	@ParameterizedTest
	@Transactional	
	void findAdminByNif(String NIF, String name) {
		Dependiente adm = dependienteService.findByNIF(NIF);
		assertThat(adm.getName()).isEqualTo(name);
	}
	
	@CsvSource({
		"18845878A"
	})
	@ParameterizedTest
	@Transactional	
	void deleteAdminByNIF(String NIF) {
		dependienteService.deleteByNIF(NIF);
		assertThat(dependienteService.count()).isEqualTo(0);
	}

}
