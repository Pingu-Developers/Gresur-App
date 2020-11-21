package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PersonalTests extends ValidatorTests{
	@ParameterizedTest
	@CsvSource({
		"Manolo, 98856332T, manolo@correo.es, Reina Mercedes",
		"Jose Luis, 96658774Y, ja@correo.es, Cadi"
	})
	/*Puesto que todos los roles de la empresa heredan de la clase Personal,
	la validación será idéntica */
	
	void validatePersonalNoErrorsTest(String name, String NIF, String email, String direccion) {
		Personal personal = new Personal();
		personal.setName(name);
		personal.setNIF(NIF);
		personal.setEmail(email);
		personal.setDireccion(direccion);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations =
				validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(0);
		
	}
	
	@ParameterizedTest
	@CsvSource({
		", 98856332T, manolo@correo.es, Reina Mercedes",
		"'', 96658774Y, ja@correo.es, Cadi",
		"' ', 78896335P, algo@correo.es, Werba"
	})
	/*Puesto que todos los roles de la empresa heredan de la clase Personal,
	la validación será idéntica */
	void validatePersonalNameNotBlankTest(String name, String NIF, String email, String direccion) {
		Personal personal = new Personal();
		personal.setName(name);
		personal.setNIF(NIF);
		personal.setEmail(email);
		personal.setDireccion(direccion);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations =
				validator.validate(personal);
		constraintViolations.removeIf(x -> !x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"María del Rosario Cayetana Paloma "
		+ "Alfonsa Victoria Eugenia Fernanda "
		+ "Teresa Francisca de Paula Lourdes "
		+ "Antonia Josefa Fausta Rita Castor  "
		+ "Dorotea Santa Esperanza Fitz-James"
		+ "Stuart y Silva Falcó y Gurtubay, 98856332T, duquesa@dearba.es, Palacio de las dueñas",
		"Ra, 96658774Y, tudiodelsol@correo.es, SOl"
	})
	/*Puesto que todos los roles de la empresa heredan de la clase Personal,
	la validación será idéntica */
	void validatePersonalNameSizeTest(String name, String NIF, String email, String direccion) {
		Personal personal = new Personal();
		personal.setName(name);
		personal.setNIF(NIF);
		personal.setEmail(email);
		personal.setDireccion(direccion);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations =
				validator.validate(personal);
		constraintViolations.removeIf(x -> !x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	

}

