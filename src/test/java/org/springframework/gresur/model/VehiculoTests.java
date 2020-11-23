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

	private Vehiculo createSUT(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
							Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos) {
		Transportista t = null;
		List<ITV> litv = new ArrayList<ITV>();
		List<Seguro> ls = new ArrayList<Seguro>();
		List<Reparacion> lr = new ArrayList<Reparacion>();
		List<Pedido> lp = new ArrayList<Pedido>();
		
		if(transportista != null && transportista > 0) {
			t = new Transportista();
		} if(ITVs != null && ITVs > 0) {
			ITV itv1 = new ITV();
			ITV itv2 = new ITV();
			litv.add(itv1);
			litv.add(itv2);
		} if(seguros != null && seguros > 0) {
			Seguro s1 = new Seguro();
			ls.add(s1);
		} if(reparaciones != null && reparaciones > 0) {
			Reparacion r1 = new Reparacion();
			Reparacion r2 = new Reparacion();
			lr.add(r1);
			lr.add(r2);
		} if(pedidos != null && pedidos > 0) {
			Pedido p1 = new Pedido();
			lp.add(p1);
		}
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula(matricula);
		vehiculo.setImagen(imagen);
		vehiculo.setCapacidad(capacidad);
		vehiculo.setDimensiones(dimensiones);
		vehiculo.setDisponibilidad(disponibilidad);
		vehiculo.setTipoVehiculo(tipoVehiculo == null ? null : TipoVehiculo.valueOf(tipoVehiculo));
		vehiculo.setMMA(MMA);
		vehiculo.setTransportista(t);
		vehiculo.setITVs(litv);
		vehiculo.setSeguros(ls);
		vehiculo.setReparaciones(lr);
		vehiculo.setPedidos(lp);
		
		return vehiculo;
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , 12.12x16x7.21, true, CAMION, 1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , 12.29x16.19x7, false, FURGONETA, 1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , '12,129x16,19x7', true, CAMION, 1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , '12.192x16.00x7.29', true, GRUA, 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoNoErrorsTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"'    ', /resources/camioneta.jpeg, 100.50 , 12x16x7, true, CAMION, 1100.29, 1, 1, 1, 0, 1",
		"    , /resources/furgo.jpeg, 100.50 , 12x16x7, false, FURGONETA, 1150.29, 0, 1, 1, 1, 1",
		"'', /resources/torito.jpeg, 100.50 , 12x16x7, true, CAMION, 1050.29, 1, 1, 1, 0, 1",
		", /resources/grua.jpeg, 100.50 , 12x16x7, true, GRUA, 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoMatriculaNotBlankTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
											Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg,  , 12x16x7, true, CAMION, 1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg,  , 12x16x7, false, FURGONETA, 1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg,  , 12x16x7, true, CAMION, 1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg,  , 12x16x7, true, GRUA, 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoCapacidadNotNullTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, -100.50 , 12x16x7, true, CAMION, 1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, -100.50 , 12x16x7, false, FURGONETA, 1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, -100.50 , 12x16x7, true, CAMION, 1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, -100.50 , 12x16x7, true, GRUA, 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoCapacidadMinTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
										Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , '      ', true, CAMION, 1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , '', false, FURGONETA, 1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 ,      , true, CAMION, 1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , , true, GRUA, 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoDimensionesNotBlankTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
												Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , 12x16.ak19x7, true, CAMION, 1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , 12x7, false, FURGONETA, 1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , 12x16ax7, true, CAMION, 1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , 12xx16x7, true, GRUA, 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoDimensionesPatternTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
												Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , 12x16x7, , CAMION, 1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , 12x16x7, , FURGONETA, 1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , 12x16x7, , CAMION, 1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , 12x16x7, , GRUA, 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoDisponibilidadNotNullTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
													Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , 12x16x7, true, , 1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , 12x16x7, false, , 1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , 12x16x7, true, , 1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , 12x16x7, true, , 11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoTipoVehiculoNotNullTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
												Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , 12x16x7, true, CAMION, , 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , 12x16x7, false, FURGONETA, , 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , 12x16x7, true, CAMION, , 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , 12x16x7, true, GRUA, , 0, 1, 1, 1, 0",

	})
	void validateVehiculoMMANotNullTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
										Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , 12x16x7, true, CAMION, -1100.29, 1, 1, 1, 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , 12x16x7, false, FURGONETA, -1150.29, 0, 1, 1, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , 12x16x7, true, CAMION, -1050.29, 1, 1, 1, 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , 12x16x7, true, GRUA, -11130.29, 0, 1, 1, 1, 0",

	})
	void validateVehiculoMMAMinTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
									Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1526 MVC, /resources/camioneta.jpeg, 100.50 , 12x16x7, true, CAMION, 1100.29, 1, 1, , 0, 1",
		"1357 SAI, /resources/furgo.jpeg, 100.50 , 12x16x7, false, FURGONETA, 1150.29, 0, 1, 0, 1, 1",
		"5356 SKJ, /resources/torito.jpeg, 100.50 , 12x16x7, true, CAMION, 1050.29, 1, 1, , 0, 1",
		"1234 ASE, /resources/grua.jpeg, 100.50 , 12x16x7, true, GRUA, 11130.29, 0, 1, 0, 1, 0",

	})
	void validateVehiculoSegurosSizeTest(String matricula, String imagen, Double capacidad, String dimensiones, Boolean disponibilidad, String tipoVehiculo, 
										Double MMA, Integer transportista, Integer ITVs, Integer seguros, Integer reparaciones, Integer pedidos){
		
		Vehiculo vehiculo = this.createSUT(matricula, imagen, capacidad, dimensiones, disponibilidad, tipoVehiculo, MMA, transportista, ITVs, seguros, reparaciones, pedidos);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Vehiculo>> constraintViolations = validator.validate(vehiculo);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}
