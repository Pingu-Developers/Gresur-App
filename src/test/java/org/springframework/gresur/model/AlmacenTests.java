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

class AlmacenTests extends ValidatorTests{	

	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro 333, 0., 0, 0",
		"C/Efe v3000 test, 7.0, 1, 1"
	})
	void validateAlmacenNoErrorsTest(String direccion, Double capacidad, Integer estanterias, Integer encargado) {
		
		Almacen almacen = new Almacen();	
		almacen.setDireccion(direccion);
		almacen.setCapacidad(capacidad);
		
		if(estanterias==0) {
			almacen.setEstanterias(new ArrayList<Estanteria>());
		} else if(estanterias==1) {
			Estanteria e = new Estanteria();
			List<Estanteria> le = new ArrayList<Estanteria>();
			le.add(e);
			almacen.setEstanterias(le);
		}
		
		if(encargado==1) {
			EncargadoDeAlmacen enc = new EncargadoDeAlmacen();
			almacen.setEncargado(enc);
		}
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@Test
	void validateAlmacenDireccionNotBlankTest() {
		
		Almacen almacen = new Almacen();
		
		almacen.setDireccion("  ");
		almacen.setCapacidad(3.9);
		almacen.setEstanterias(new ArrayList<Estanteria>());
		almacen.setEncargado(new EncargadoDeAlmacen());
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotNull"));
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Min"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}
	
	@Test
	void validateAlmacenCapacidadNotNullTest() {
		
		Almacen almacen = new Almacen();
		
		almacen.setDireccion("Calle Testeada");
		almacen.setCapacidad(null);
		almacen.setEstanterias(new ArrayList<Estanteria>());
		almacen.setEncargado(new EncargadoDeAlmacen());
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Min"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@Test
	void validateAlmacenCapacidadMinTest() {
		
		Almacen almacen = new Almacen();
		
		almacen.setDireccion("Calle Testeada");
		almacen.setCapacidad(-0.001);
		almacen.setEstanterias(new ArrayList<Estanteria>());
		almacen.setEncargado(new EncargadoDeAlmacen());
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Almacen>> constraintViolations = validator.validate(almacen);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotNull"));
		assertThat(constraintViolations.size()).isEqualTo(1);
		
	}

}
