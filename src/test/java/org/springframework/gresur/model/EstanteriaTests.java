package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EstanteriaTests extends ValidatorTests{
	
	private Estanteria createSUT(String categoria, Double capacidad, Long almacen) {
		Almacen alm = null;
		
		if (almacen != null && almacen > 0) {
			alm = new Almacen();
		}
				
		Estanteria estanteria = new Estanteria();
		estanteria.setCategoria(categoria == null ? null : Categoria.valueOf(categoria));
		estanteria.setCapacidad(capacidad);	
		estanteria.setAlmacen(alm);
		
		return estanteria;
	}
	
	@ParameterizedTest
	@CsvSource({
		"PINTURAS, 10000.00, 1",
		"SILICES, 1230.19, 2"
	})
	/* EL ATRIBUTO DE LA LISTA ES 0 SI ESTA VACIA, 1 SI TIENE ELEMENTOS*/
	void validateEstanteriaNoErrorsTest(String categoria, Double capacidad, Long almacen) {
		Estanteria estanteria = this.createSUT(categoria, capacidad, almacen);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Estanteria>> constraintViolations = validator.validate(estanteria);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		", 10000.00, 1",
		" , 1230.19, 2"
	})
	void validateEstanteriaCategoriaNotNullTest(String categoria, Double capacidad, Long almacen) {
		Estanteria estanteria = this.createSUT(categoria, capacidad, almacen);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Estanteria>> constraintViolations = validator.validate(estanteria);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"CALEFACCION, , 1",
		"PINTURAS, , 2"
	})
	void validateEstanteriaCapacidadNotNullTest(String categoria, Double capacidad, Long almacen) {
		Estanteria estanteria = this.createSUT(categoria, capacidad, almacen);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Estanteria>> constraintViolations = validator.validate(estanteria);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"SILICES, -1.20, 1",
		"CALEFACCION, -20.1039, 2"
	})
	void validateEstanteriaCapacidadMinTest(String categoria, Double capacidad, Long almacen) {
		Estanteria estanteria = this.createSUT(categoria, capacidad, almacen);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Estanteria>> constraintViolations = validator.validate(estanteria);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"LADRILLOS, 101029.2030, ",
		"CALEFACCION, 10303.239, "
	})
	void validateEstanteriaAlmacenNotOptional(String categoria, Double capacidad, Long almacen) {
		Estanteria estanteria = this.createSUT(categoria, capacidad, almacen);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Estanteria>> constraintViolations = validator.validate(estanteria);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

}
