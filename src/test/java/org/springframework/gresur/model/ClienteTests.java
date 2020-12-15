package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClienteTests extends ValidatorTests{
	
	private Cliente createSUT(String name, String NIF,String tlf, String email, String direccion,Integer ListaFactura) {
		List<FacturaEmitida> lfe = new ArrayList<FacturaEmitida>();
		
		if(ListaFactura != null && ListaFactura > 0) {
			FacturaEmitida f1 = new FacturaEmitida();
			FacturaEmitida f2 = new FacturaEmitida();
			lfe.add(f1);
			lfe.add(f2);
		}
		Cliente cliente = new Cliente();
		cliente.setName(name);
		cliente.setNIF(NIF);
		cliente.setTlf(tlf);
		cliente.setEmail(email);
		cliente.setDireccion(direccion);
		cliente.setFacturasEmitidas(lfe);
		
		return cliente;
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T,0",
		"Wos, 96658774Y,987654321 , ja@correo.es, Cad,1",
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturis, 92341231P,567894321,borjar20@gmail.com,Tomares,1"
	})
	void validateClienteNoErrorsTest(String name, String NIF,String tlf, String email, String direccion,Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"'  ', 98856332T,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T,0",
		"'', 96658774Y,987654321 , ja@correo.es, Cad,1",
		", 92341231P,567894321,borjar20@gmail.com,Tomares,1"
	})
	void validateClienteNameNotBlankTest(String name, String NIF,String tlf, String email, String direccion,Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturias, 98856332T,123456789, manolo@correo.es, Aljamir, 1",
		"Wo, 96658774Y, 987654321 , ja@correo.es, Cad, "
		})
	void validateClienteNameSizeTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe Jose Carlos Borja Paco Lourdes Josefa, '  ',123456789, manolo@correo.es, Aljamir, 1",
		"Woassds, '', 987654321, ja@correo.es, Cad, ",
		"Pepito, , 987362712, patata@yopmail.com, C/ calle, 0"
		
		})
	void validateClienteNIFNotBlankTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, nodni,123456789, manolo@correo.es,Aljamir Software S.L. Glorieta Fernando Quiñones T,0",
		"Wos, 22ff,987654321 , ja@correo.es, Cad,1",
		"Pepe Jose Carlos Borja Paco Lourdes Josefa Asturis, FGD34, 880654321, borjar20@gmail.com,Tomares,1"
	})
	void validateClienteNIFPatternTest(String name, String NIF,String tlf, String email, String direccion,Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe Jose Carlos Borja Paco Lourdes Josefa, 12345678A, 123456789, '   ', Aljamir, 1",
		"Woassds, 98765432C, 987654321, '', Cad, ",
		"Pepito, 23456789W, 987362712, , C/ calle, 0"
		
		})
	void validateClienteEmailNotBlankTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Email"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antoñito, 12345678A, 987362711, correoFalso, Aljamir, 1",
		"Woassds, 98765432C, 987654321, @correo.es, Cad, ",
		"Pepito, 23456789W, 987362712, yopmail.com, C/ calle, 0"
		
		})
	void validateClienteEmailEmailTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antoñito, 12345678A, ' ', manolo@correo.eso, Aljamir, 1",
		"Woassds, 98765432C, '', manolin@correo.es, Cad, ",
		"Pepito, 23456789W, , patata@yopmail.com, C/ calle, 0"
		})
	void validateClienteTlfNotBlankTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T, 23456789, manolo@correo.es, Aljamir, 1",
		"Antonio, 98856332T, 0123456789, gfg3@correo.es, Aljamir, ",
		"Carlos, 98856290S, 1939as829, asd@correo.es, Aljamir, 0",
		})
	void validateClienteTlfPatternTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);	
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T, 987362712, manolo@correo.es, '   ', 1",
		"Antonio, 98856332T, 987362712, gfg3@correo.es, '', ",
		"Carlos, 98856290S, 987362712, asd@correo.es, , 0",
		})
	void validateClienteDireccionNotBlankTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);	

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 98856332T, 123456789, manolo@correo.es, Al, 0",
		"Pepe2, 98856332X, 123456789, manolo2@correo.es, Aljamir Software S.L. Glorieta Fernando Quiñones Tengo que inventarme cosas mas largas porque esto ahora ocupa cien caracteres, 1"
		})
	void validateClienteDireccionSizeTest(String name, String NIF,String tlf, String email, String direccion, Integer ListaFactura) {
		Cliente cliente = this.createSUT(name, NIF, tlf, email, direccion, ListaFactura);	
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}
