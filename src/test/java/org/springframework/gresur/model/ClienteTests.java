package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClienteTests extends ValidatorTests{
	
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T,0",
		"Wos, 96658774Y,987654321 , ja@correo.es, Cad,1",
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturis, 92341231P,567894321,borjar20@gmail.com,Tomares,1"
	})
	void validateClienteNoErrorsTest(String name, String NIF,String tlf, String email, String direccion,Integer ListaFactura) {
		Cliente cliente = new Cliente();
		cliente.setName(name);
		cliente.setNIF(NIF);
		cliente.setTlf(tlf);
		cliente.setEmail(email);
		cliente.setDireccion(direccion);
		if(ListaFactura == 0) {
			List<FacturaEmitida> l = new ArrayList<>();
			cliente.setFacturasEmitidas(l);
		}
		if(ListaFactura == 1) {
			List<FacturaEmitida> l = new ArrayList<>();
			FacturaEmitida fe = new FacturaEmitida();
			l.add(fe);
			cliente.setFacturasEmitidas(l);
		}

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	
	@Test
	void validateNameNotBlankError() {
		Cliente cliente = new Cliente();
		cliente.setName(" ");
		cliente.setNIF("98856332T");
		cliente.setTlf("567894321");
		cliente.setEmail("ja@correo.es");
		cliente.setDireccion("borjar20@gmail.com");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturias, 98856332T,123456789, manolo@correo.es,Aljamir",
		"Wo, 96658774Y,987654321 , ja@correo.es, Cad"
		})
	void validateNameSizeError(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = new Cliente();
		cliente.setName(name);
		cliente.setNIF(NIF);
		cliente.setTlf(tlf);
		cliente.setEmail(email);
		cliente.setDireccion(direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@Test
	void validateNifNotBlankError() {
		Cliente cliente = new Cliente();
		cliente.setName("Wos");
		cliente.setNIF(" ");
		cliente.setTlf("567894321");
		cliente.setEmail("ja@correo.es");
		cliente.setDireccion("borjar20@gmail.com");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@Test
	void validateEmailNotBlankError() {
		Cliente cliente = new Cliente();
		cliente.setName("Wos");
		cliente.setNIF("96658774Y");
		cliente.setTlf("567894321");
		cliente.setEmail(" ");
		cliente.setDireccion("borjar20@gmail.com");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Email"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@Test
	void validateEmailNotEmailError() {
		Cliente cliente = new Cliente();
		cliente.setName("Wos");
		cliente.setNIF("96658774Y");
		cliente.setTlf("567894321");
		cliente.setEmail("correo");
		cliente.setDireccion("borjar20@gmail.com");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@Test
	void validateTlfNotNullError() {
		Cliente cliente = new Cliente();
		cliente.setName("Wos");
		cliente.setNIF("96658774Y");
		cliente.setTlf("56894321");
		cliente.setEmail("ja@correo.es");
		cliente.setDireccion("borjar20@gmail.com");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T,23456789, manolo@correo.es,Aljamir",
		"Pepe, 98856332T,0123456789, manolo@correo.es,Aljamir",
		})
	void validateTlfPatternError(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = new Cliente();
		cliente.setName(name);
		cliente.setNIF(NIF);
		cliente.setTlf(tlf);
		cliente.setEmail(email);
		cliente.setDireccion(direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	void validateDirecionNotBlankError() {
		Cliente cliente = new Cliente();
		cliente.setName("Wos");
		cliente.setNIF("96658774Y");
		cliente.setTlf("567894321");
		cliente.setEmail("borjar20@gmail.com");
		cliente.setDireccion(" ");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T,123456789, manolo@correo.es,Al",
		"Pepe, 98856332T,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T"
		})
	void validateDirecionSizeError(String name, String NIF,String tlf, String email, String direccion) {
		Cliente cliente = new Cliente();
		cliente.setName(name);
		cliente.setNIF(NIF);
		cliente.setTlf(tlf);
		cliente.setEmail(email);
		cliente.setDireccion(direccion);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations =
				validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}
