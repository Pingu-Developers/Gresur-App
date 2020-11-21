package org.springframework.gresur.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.repository.PersonalRepository;

import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AdministradorServiceTests {
	
	@Autowired
	PersonalService<Personal, PersonalRepository<Personal>> personalService;
	
	@Autowired
	AdministradorService administradorService;

	@ParameterizedTest
	@CsvSource({
		"Manolo, 98856332T, manolo@correo.es, Reina Mercedes"
	})
	void addAdministradorNoErrorsTest(String name, String NIF, String email, String direccion) {
		Administrador adm = new Administrador();
		adm.setName(name);
		adm.setNIF(NIF);
		adm.setEmail(email);
		adm.setDireccion(direccion);
		
		administradorService.save(adm);
		assertTrue(administradorService.count() == 1L);	
	}
	
}
