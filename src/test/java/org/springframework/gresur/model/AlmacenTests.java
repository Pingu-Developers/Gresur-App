package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AlmacenTests extends ValidatorTests{	
	
	private Almacen createSUT(String direccion, Double capacidad) {
		Almacen almacen = new Almacen();
		almacen.setDireccion(direccion);
		almacen.setCapacidad(capacidad);
		return almacen;
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro 333, 0.",
		"C/Efe v3000 test, 7.0"
	})
	void validateAlmacenNoErrorsTest(String direccion, Double capacidad) {
		Almacen almacen = this.createSUT(direccion, capacidad);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);		
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"' ', 3.9",
		"'' , 19.29",
		" , 392.9"
	})
	void validateAlmacenDireccionNotBlankTest(String direccion, Double capacidad) {
		Almacen almacen = this.createSUT(direccion, capacidad);

		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/, 3.9",
		"C/ esto es un nombre muy largo que debe tener mas de cincuenta caracteres, 19.29",
		"C, 392.9"
	})
	void validateAlmacenDireccionSizeTest(String direccion, Double capacidad) {
		Almacen almacen = this.createSUT(direccion, capacidad);

		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"C/ Ligastorro, ",
		"C/ Prueba , ",
		"C/ uwu , "
	})
	void validateAlmacenCapacidadNotNullTest(String direccion, Double capacidad) {
		Almacen almacen = this.createSUT(direccion, capacidad);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Min"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/ Ligastorro, -0.0001",
		"C/ Prueba, -1.2923",
		"C/ uwu, -283.3929"
	})
	void validateAlmacenCapacidadMinTest(String direccion, Double capacidad) {
		Almacen almacen = this.createSUT(direccion, capacidad);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotNull"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}

}
