package org.springframework.gresur.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.repository.AdministradorRepository;
import org.springframework.gresur.repository.PersonalRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.gresur.service.PersonalService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PersonalTests {
	
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
		
		administradorService.add(adm);
		assertTrue(administradorService.count() == 1L);	
	}
	
	@Test
	void addPersonalNoErrorsTest() {
		Personal personal = new Personal();
		personal.setName("Admin");
		personal.setNIF("21154665R");
		personal.setEmail("admin@gresur.es");
		personal.setDireccion("Calle");
		personalService.add(personal);
		assertTrue(personalService.count() == 1L);
		
	}
	
}
