package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VehiculoTests extends ValidatorTests{

	private Vehiculo createSUT(String matricula, String imagen, Double capacidad, String tipoVehiculo, 
							Double MMA) {

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula(matricula);
		vehiculo.setImagen(imagen);
		vehiculo.setCapacidad(capacidad);
		vehiculo.setTipoVehiculo(tipoVehiculo == null ? null : TipoVehiculo.valueOf(tipoVehiculo));
		vehiculo.setMMA(MMA);
		
		return vehiculo;
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, CAMION, 1100.29",
		"1357 SAI, /resources/furgo.jpeg, 100.50, FURGONETA, 1150.29",
		"5356 SKJ, /resources/torito.jpeg, 100.50, CAMION, 1050.29",
		"1234 ASE, /resources/grua.jpeg, 100.50, GRUA, 11130.29",

	})
	void validateVehiculoNoErrorsTest(String matricula, String imagen, Double capacidad, String tipoVehiculo, 
									Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"'    ', /resources/camioneta.jpeg, 100.50, CAMION, 1100.29",
		"    , /resources/furgo.jpeg, 100.50, FURGONETA, 1150.29",
		"'', /resources/torito.jpeg, 100.50, CAMION, 1050.29",
		", /resources/grua.jpeg, 100.50, GRUA, 11130.29",

	})
	void validateVehiculoMatriculaNotBlankTest(String matricula, String imagen, Double capacidad, String tipoVehiculo, 
											Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg,  , CAMION, 1100.29",
		"1357 SAI, /resources/furgo.jpeg,  , FURGONETA, 1150.29",
		"5356 SKJ, /resources/torito.jpeg,  , CAMION, 1050.29",
		"1234 ASE, /resources/grua.jpeg,  , GRUA, 11130.29",

	})
	void validateVehiculoCapacidadNotNullTest(String matricula, String imagen, Double capacidad, String tipoVehiculo, 
									Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, -100.50, CAMION, 1100.29",
		"1357 SAI, /resources/furgo.jpeg, -100.50, FURGONETA, 1150.29",
		"5356 SKJ, /resources/torito.jpeg, -100.50, CAMION, 1050.29",
		"1234 ASE, /resources/grua.jpeg, -100.50, GRUA, 11130.29",

	})
	void validateVehiculoCapacidadMinTest(String matricula, String imagen, Double capacidad, String tipoVehiculo, 
										Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , , 1100.29",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , , 1150.29",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , , 1050.29",
		"1234 ASE, /resources/grua.jpeg, 100.50 , , 11130.29",

	})
	void validateVehiculoTipoVehiculoNotNullTest(String matricula, String imagen, Double capacidad, String tipoVehiculo, 
												Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, CAMION, ",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , FURGONETA, ",
		"5356 SKJ, /resources/torito.jpeg, 100.50, CAMION, ",
		"1234 ASE, /resources/grua.jpeg, 100.50, GRUA, ",

	})
	void validateVehiculoMMANotNullTest(String matricula, String imagen, Double capacidad , String tipoVehiculo, 
										Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50, CAMION, -1100.29",
		"1357 SAI, /resources/furgo.jpeg, 100.50, FURGONETA, -1150.29",
		"5356 SKJ, /resources/torito.jpeg, 100.50, CAMION, -1050.29",
		"1234 ASE, /resources/grua.jpeg, 100.50, GRUA, -11130.29",

	})
	void validateVehiculoMMAMinTest(String matricula, String imagen, Double capacidad, String tipoVehiculo, 
									Double MMA){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, tipoVehiculo, MMA);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
}