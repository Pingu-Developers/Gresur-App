package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VehiculoTests extends ValidatorTests{

	private Vehiculo createSUT(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
							Double MMA, Integer reparaciones) {
		
		List<Reparacion> lr = new ArrayList<Reparacion>();
		
		if(reparaciones != null && reparaciones > 0) {
			Reparacion r1 = new Reparacion();
			Reparacion r2 = new Reparacion();
			lr.add(r1);
			lr.add(r2);
		}
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula(matricula);
		vehiculo.setImagen(imagen);
		vehiculo.setCapacidad(capacidad);
		vehiculo.setDisponibilidad(disponibilidad);
		vehiculo.setTipoVehiculo(tipoVehiculo == null ? null : TipoVehiculo.valueOf(tipoVehiculo));
		vehiculo.setMMA(MMA);
		vehiculo.setReparaciones(lr);
		
		return vehiculo;
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, true, CAMION, 1100.29, 0",
		"1357 SAI, /resources/furgo.jpeg, 100.50, false, FURGONETA, 1150.29, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50, true, CAMION, 1050.29, 0",
		"1234 ASE, /resources/grua.jpeg, 100.50, true, GRUA, 11130.29, 1",

	})
	void validateVehiculoNoErrorsTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"'    ', /resources/camioneta.jpeg, 100.50, true, CAMION, 1100.29, 0",
		"    , /resources/furgo.jpeg, 100.50, false, FURGONETA, 1150.29, 1",
		"'', /resources/torito.jpeg, 100.50, true, CAMION, 1050.29, 0",
		", /resources/grua.jpeg, 100.50, true, GRUA, 11130.29, 1",

	})
	void validateVehiculoMatriculaNotBlankTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
											Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg,  , true, CAMION, 1100.29, 0",
		"1357 SAI, /resources/furgo.jpeg,  , false, FURGONETA, 1150.29, 1",
		"5356 SKJ, /resources/torito.jpeg,  , true, CAMION, 1050.29, 0",
		"1234 ASE, /resources/grua.jpeg,  , true, GRUA, 11130.29, 1",

	})
	void validateVehiculoCapacidadNotNullTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, -100.50, true, CAMION, 1100.29, 0",
		"1357 SAI, /resources/furgo.jpeg, -100.50, false, FURGONETA, 1150.29, 1",
		"5356 SKJ, /resources/torito.jpeg, -100.50, true, CAMION, 1050.29, 0",
		"1234 ASE, /resources/grua.jpeg, -100.50, true, GRUA, 11130.29, 1",

	})
	void validateVehiculoCapacidadMinTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
										Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
		
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, , CAMION, 1100.29, 0",
		"1357 SAI, /resources/furgo.jpeg, 100.50, , FURGONETA, 1150.29, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , , CAMION, 1050.29, 0",
		"1234 ASE, /resources/grua.jpeg, 100.50 , , GRUA, 11130.29, 1",

	})
	void validateVehiculoDisponibilidadNotNullTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
													Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , true, , 1100.29, 0",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , false, , 1150.29, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , true, , 1050.29, 0",
		"1234 ASE, /resources/grua.jpeg, 100.50 , true, , 11130.29, 1",

	})
	void validateVehiculoTipoVehiculoNotNullTest(String matricula, String imagen, Double capacidad , Boolean disponibilidad, String tipoVehiculo, 
												Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, true, CAMION, , 0",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , false, FURGONETA, , 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50, true, CAMION, , 0",
		"1234 ASE, /resources/grua.jpeg, 100.50, true, GRUA, , 1",

	})
	void validateVehiculoMMANotNullTest(String matricula, String imagen, Double capacidad , Boolean disponibilidad, String tipoVehiculo, 
										Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, true, CAMION, -1100.29, 0",
		"1357 SAI, /resources/furgo.jpeg, 100.50, false, FURGONETA, -1150.29, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50, true, CAMION, -1050.29, 0",
		"1234 ASE, /resources/grua.jpeg, 100.50, true, GRUA, -11130.29, 1",

	})
	void validateVehiculoMMAMinTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA, Integer reparaciones){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA, reparaciones);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
}