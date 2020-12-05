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
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class AdministradorServiceTests {
	
	@Autowired
	protected AdministradorService administradorService;
	
	@BeforeEach
	@Transactional
	void initAll() {
		Administrador adm = new Administrador();
		adm.setName("Jose Luis Gresur");
		adm.setNIF("18845878A");
		adm.setEmail("e1@email.com");
		adm.setTlf("696823445");
		adm.setDireccion("Av. Reina Mercedes");
		adm.setNSS("12 1234123890");
		adm.setImage("/resources/foto.png");
		
		administradorService.save(adm);
	}
	@AfterEach
	@Transactional
	void clearAll() {
		administradorService.deleteAll();
	}
	
	//Tests
	
	@CsvSource({
		"18845878A, Jose Luis Gresur"
	})
	@ParameterizedTest
	@Transactional	
	void findAdminByNif(String NIF, String name) {
		Administrador adm = administradorService.findByNIF(NIF);
		assertThat(adm.getName()).isEqualTo(name);
	}
	
	@CsvSource({
		"18845878A"
	})
	@ParameterizedTest
	@Transactional	
	void deleteAdminByNIF(String NIF) {
		administradorService.deleteByNIF(NIF);
		assertThat(administradorService.count()).isEqualTo(0);
	}
		

}
