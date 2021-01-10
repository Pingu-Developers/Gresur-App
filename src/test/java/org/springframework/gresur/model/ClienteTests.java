package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClienteTests extends ValidatorTests{
	
	private Cliente createSUT(String name, String NIF,String tlf, String email, String direccion) {
		
		Cliente cliente = new Cliente();
		cliente.setName(name);
		cliente.setNIF(NIF);
		cliente.setTlf(tlf);
		cliente.setEmail(email);
		cliente.setDireccion(direccion);
		
		return cliente;
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T",
		"Wos, 96658774Y,987654321 , ja@correo.es, Cad",
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturis, 92341231P,567894321,borjar20@gmail.com,Tomares"
	})
	void validateClienteNoErrorsTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"'  ', 98856332T,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T",
		"'', 96658774Y,987654321 , ja@correo.es, Cad",
		", 92341231P,567894321,borjar20@gmail.com,Tomares"
	})
	void validateClienteNameNotBlankTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturias, 98856332T,123456789, manolo@correo.es, Aljamir",
		"Wo, 96658774Y, 987654321 , ja@correo.es, Cad"
		})
	void validateClienteNameSizeTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe Jose Carlos Borja Paco Lourdes Josefa, '  ',123456789, manolo@correo.es, Aljamir",
		"Woassds, '', 987654321, ja@correo.es, Cad",
		"Pepito, , 987362712, patata@yopmail.com, C/ calle"
		
		})
	void validateClienteNIFNotBlankTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, nodni,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T",
		"Wos, 22ff,987654321 , ja@correo.es, Cad",
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturis, FGD34, 880654321, borjar20@gmail.com,Tomares"
	})
	void validateClienteNIFPatternTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe Jose Carlos Borja Paco Lourdes Josefa, 12345678A, 123456789, '   ', Aljamir",
		"Woassds, 98765432C, 987654321, '', Cad",
		"Pepito, 23456789W, 987362712, , C/ calle"
		
		})
	void validateClienteEmailNotBlankTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Email"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antoñito, 12345678A, 987362711, correoFalso, Aljamir",
		"Woassds, 98765432C, 987654321, @correo.es, Cad",
		"Pepito, 23456789W, 987362712, yopmail.com, C/ calle"
		
		})
	void validateClienteEmailEmailTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antoñito, 12345678A, ' ', manolo@correo.eso, Aljamir",
		"Woassds, 98765432C, '', manolin@correo.es, Cad",
		"Pepito, 23456789W, , patata@yopmail.com, C/ calle"
		})
	void validateClienteTlfNotBlankTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T, 23456789, manolo@correo.es, Aljamir",
		"Antonio, 98856332T, 0123456789, gfg3@correo.es, Aljamir",
		"Carlos, 98856290S, 1939as829, asd@correo.es, Aljamir",
		})
	void validateClienteTlfPatternTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);	
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T, 987362712, manolo@correo.es, '   '",
		"Antonio, 98856332T, 987362712, gfg3@correo.es, ''",
		"Carlos, 98856290S, 987362712, asd@correo.es, ",
		})
	void validateClienteDireccionNotBlankTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);	

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T, 123456789, manolo@correo.es, Al",
		"Pepe2, 98856332X, 123456789, manolo2@correo.es, Aljamir Software S.L. Glorieta Fernando Quiñones Tengo que inventarme cosas mas largas porque esto ahora ocupa cien caracteres"
		})
	void validateClienteDireccionSizeTest(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion);	
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}
