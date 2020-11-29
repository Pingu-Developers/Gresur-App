package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AlmacenTests extends ValidatorTests{	
	
	private Almacen createSUT(String direccion, Double capacidad, Integer encargado) {
		EncargadoDeAlmacen e = null;
		
		if (encargado != null && encargado > 0) {
			e = new EncargadoDeAlmacen();
		}
		Almacen almacen = new Almacen();
		almacen.setDireccion(direccion);
		almacen.setCapacidad(capacidad);
		almacen.setEncargado(e);
		return almacen;
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro 333, 0., 0",
		"C/Efe v3000 test, 7.0, 1"
	})
	void validateAlmacenNoErrorsTest(String direccion, Double capacidad, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, encargado);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);		
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"' ', 3.9, 1",
		"'' , 19.29, 0",
		" , 392.9, 0"
	})
	void validateAlmacenDireccionNotBlankTest(String direccion, Double capacidad, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, encargado);

		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/, 3.9, 1",
		"C/ esto es un nombre muy largo que debe tener mas de cincuenta caracteres, 19.29, 0",
		"C, 392.9, 0"
	})
	void validateAlmacenDireccionSizeTest(String direccion, Double capacidad, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, encargado);

		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"C/ Ligastorro, , 1",
		"C/ Prueba , , ",
		"C/ uwu , , 0"
	})
	void validateAlmacenCapacidadNotNullTest(String direccion, Double capacidad, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, encargado);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Min"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/ Ligastorro, -0.0001 , 1",
		"C/ Prueba, -1.2923, ",
		"C/ uwu, -283.3929, 0"
	})
	void validateAlmacenCapacidadMinTest(String direccion, Double capacidad, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, encargado);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotNull"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}

}
