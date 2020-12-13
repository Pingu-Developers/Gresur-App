package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
	
	
	/* FIND-REMOVE TESTS*/
	
	@Test
	@Transactional	
	void findAdminByNif() {
		Administrador adm = administradorService.findByNIF("18845878A");
		assertThat(adm.getName()).isEqualTo("Jose Luis Gresur");
	}
	
	@Test
	@Transactional
	void findAdminByNifNotFound() {
		Administrador adm = administradorService.findByNIF("18845878C");
		assertThat(adm).isEqualTo(null);
	}
	
	@Test
	@Transactional	
	void deleteAdminByNIF() {
		administradorService.deleteByNIF("18845878A");
		assertThat(administradorService.count()).isEqualTo(0);
	}
	
	@Test
	@Transactional	
	void deleteAdminByNIFNotFound() {
		administradorService.deleteByNIF("18845878S");
		assertThat(administradorService.count()).isEqualTo(1);
	}

}
