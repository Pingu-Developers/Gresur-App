package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VehiculoTests extends ValidatorTests{

	private Vehiculo createSUT(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
							Double MMA) {

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula(matricula);
		vehiculo.setImagen(imagen);
		vehiculo.setCapacidad(capacidad);
		vehiculo.setDisponibilidad(disponibilidad);
		vehiculo.setTipoVehiculo(tipoVehiculo == null ? null : TipoVehiculo.valueOf(tipoVehiculo));
		vehiculo.setMMA(MMA);
		
		return vehiculo;
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, true, CAMION, 1100.29",
		"1357 SAI, /resources/furgo.jpeg, 100.50, false, FURGONETA, 1150.29",
		"5356 SKJ, /resources/torito.jpeg, 100.50, true, CAMION, 1050.29",
		"1234 ASE, /resources/grua.jpeg, 100.50, true, GRUA, 11130.29",

	})
	void validateVehiculoNoErrorsTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"'    ', /resources/camioneta.jpeg, 100.50, true, CAMION, 1100.29",
		"    , /resources/furgo.jpeg, 100.50, false, FURGONETA, 1150.29",
		"'', /resources/torito.jpeg, 100.50, true, CAMION, 1050.29",
		", /resources/grua.jpeg, 100.50, true, GRUA, 11130.29",

	})
	void validateVehiculoMatriculaNotBlankTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
											Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg,  , true, CAMION, 1100.29",
		"1357 SAI, /resources/furgo.jpeg,  , false, FURGONETA, 1150.29",
		"5356 SKJ, /resources/torito.jpeg,  , true, CAMION, 1050.29",
		"1234 ASE, /resources/grua.jpeg,  , true, GRUA, 11130.29",

	})
	void validateVehiculoCapacidadNotNullTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, -100.50, true, CAMION, 1100.29",
		"1357 SAI, /resources/furgo.jpeg, -100.50, false, FURGONETA, 1150.29",
		"5356 SKJ, /resources/torito.jpeg, -100.50, true, CAMION, 1050.29",
		"1234 ASE, /resources/grua.jpeg, -100.50, true, GRUA, 11130.29",

	})
	void validateVehiculoCapacidadMinTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
										Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
		
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, , CAMION, 1100.29",
		"1357 SAI, /resources/furgo.jpeg, 100.50, , FURGONETA, 1150.29",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , , CAMION, 1050.29",
		"1234 ASE, /resources/grua.jpeg, 100.50 , , GRUA, 11130.29",

	})
	void validateVehiculoDisponibilidadNotNullTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
													Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , true, , 1100.29",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , false, , 1150.29",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , true, , 1050.29",
		"1234 ASE, /resources/grua.jpeg, 100.50 , true, , 11130.29",

	})
	void validateVehiculoTipoVehiculoNotNullTest(String matricula, String imagen, Double capacidad , Boolean disponibilidad, String tipoVehiculo, 
												Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, true, CAMION, ",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , false, FURGONETA, ",
		"5356 SKJ, /resources/torito.jpeg, 100.50, true, CAMION, ",
		"1234 ASE, /resources/grua.jpeg, 100.50, true, GRUA, ",

	})
	void validateVehiculoMMANotNullTest(String matricula, String imagen, Double capacidad , Boolean disponibilidad, String tipoVehiculo, 
										Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, true, CAMION, -1100.29",
		"1357 SAI, /resources/furgo.jpeg, 100.50, false, FURGONETA, -1150.29",
		"5356 SKJ, /resources/torito.jpeg, 100.50, true, CAMION, -1050.29",
		"1234 ASE, /resources/grua.jpeg, 100.50, true, GRUA, -11130.29",

	})
	void validateVehiculoMMAMinTest(String matricula, String imagen, Double capacidad, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, disponibilidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
}