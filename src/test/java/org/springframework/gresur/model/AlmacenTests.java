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
	
	private Almacen createSUT(String direccion, Double capacidad, Integer estanterias, Integer encargado) {
		List<Estanteria> le = new ArrayList<Estanteria>();
		EncargadoDeAlmacen e = null;
		
		if(estanterias != null && estanterias > 0) {
			Estanteria e1 = new Estanteria();
			Estanteria e2 = new Estanteria();
			le.add(e1);
			le.add(e2);
		} if (encargado != null && encargado > 0) {
			e = new EncargadoDeAlmacen();
		}
		Almacen almacen = new Almacen();
		almacen.setDireccion(direccion);
		almacen.setCapacidad(capacidad);
		almacen.setEstanterias(le);
		almacen.setEncargado(e);
		return almacen;
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro 333, 0., 0, 0",
		"C/Efe v3000 test, 7.0, 1, 1"
	})
	void validateAlmacenNoErrorsTest(String direccion, Double capacidad, Integer estanterias, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, estanterias, encargado);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);		
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"' ', 3.9, 1, 1",
		"'' , 19.29, 0, ",
		" , 392.9, 1, 0"
	})
	void validateAlmacenDireccionNotBlankTest(String direccion, Double capacidad, Integer estanterias, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, estanterias, encargado);

		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/ Ligastorro, , 1, 1",
		"C/ Prueba , , 0, ",
		"C/ uwu , , 1, 0"
	})
	void validateAlmacenCapacidadNotNullTest(String direccion, Double capacidad, Integer estanterias, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, estanterias, encargado);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/ Ligastorro, -0.0001 , 1, 1",
		"C/ Prueba, -1.2923, 0, ",
		"C/ uwu, -283.3929 , 1, 0"
	})
	void validateAlmacenCapacidadMinTest(String direccion, Double capacidad, Integer estanterias, Integer encargado) {
		Almacen almacen = this.createSUT(direccion, capacidad, estanterias, encargado);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}

}
