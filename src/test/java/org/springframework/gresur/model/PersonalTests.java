package org.springframework.gresur.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class AdministradorModelTests {
	
	@ParameterizedTest
	@CsvSource({
		"Manolo, 98856332T, manolo@correo.es, Reina Mercedes"
	})
	void validateAdministradorNoErrorsTest(String name, String NIF, String email, String direccion) {
		Administrador adm = new Administrador();
		adm.setName(name);
		adm.setNIF(NIF);
		adm.setEmail(email);
		adm.setDireccion(direccion);
		
		Validator validator = ValidatorTests.createValidator();
	}

}
