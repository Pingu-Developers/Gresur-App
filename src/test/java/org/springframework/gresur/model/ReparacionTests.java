package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReparacionTests extends ValidatorTests{

	private Reparacion createSUT(String fechaEntrada, String fechaSalida, Integer recibida, Integer vehiculo) {
		FacturaRecibida fr = null;
		Vehiculo v = null;
		
		if(recibida != null && recibida > 0) {
			fr = new FacturaRecibida();
		} if(vehiculo != null && vehiculo > 0) {
			v = new Vehiculo();
		}
		Reparacion rep = new Reparacion();
		rep.setFechaEntradaTaller(LocalDate.parse(fechaEntrada, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		rep.setFechaSalidaTaller(LocalDate.parse(fechaSalida, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		rep.setRecibidas(fr);
		rep.setVehiculo(v);
		
		return rep;
	}
		
	@ParameterizedTest
	@CsvSource({
		"12/11/2019, 11/12/2019, 1, 1",
		"12/09/2020, 11/03/2020, 1, 1"
	})
	void validateReparacionNoErrorsTest(String fechaEntrada, String fechaSalida, Integer recibida, Integer vehiculo) {
		Reparacion reparacion = this.createSUT(fechaEntrada, fechaSalida, recibida, vehiculo);
	
		Validator validator = createValidator();
		Set<ConstraintViolation<Reparacion>> constraintViolations = validator.validate(reparacion);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	//TODO Validacion de DateTimeFormat aqui?
	
	@ParameterizedTest
	@CsvSource({
		"12/11/2029, 11/12/2049, 1, 1",
		"12/09/2031, 11/03/2020, 1, 1"
	})
	void validateReparacionFechaEntradaPastOrPresentTest(String fechaEntrada, String fechaSalida, Integer recibida, Integer vehiculo) {
		Reparacion reparacion = this.createSUT(fechaEntrada, fechaSalida, recibida, vehiculo);
	
		Validator validator = createValidator();
		Set<ConstraintViolation<Reparacion>> constraintViolations = validator.validate(reparacion);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"12/11/2019, 11/12/2019, , 1",
		"12/09/2020, 11/03/2020, 0, 1"
	})
	void validateReparacionRecibidasNotNullTest(String fechaEntrada, String fechaSalida, Integer recibida, Integer vehiculo) {
		Reparacion reparacion = this.createSUT(fechaEntrada, fechaSalida, recibida, vehiculo);
	
		Validator validator = createValidator();
		Set<ConstraintViolation<Reparacion>> constraintViolations = validator.validate(reparacion);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"12/11/2019, 11/12/2019, 1, ",
		"12/09/2020, 11/03/2020, 1, 0"
	})
	void validateReparacionVehiculoNotNullTest(String fechaEntrada, String fechaSalida, Integer recibida, Integer vehiculo) {
		Reparacion reparacion = this.createSUT(fechaEntrada, fechaSalida, recibida, vehiculo);
	
		Validator validator = createValidator();
		Set<ConstraintViolation<Reparacion>> constraintViolations = validator.validate(reparacion);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

}
