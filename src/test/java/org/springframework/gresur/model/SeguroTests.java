package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SeguroTests extends ValidatorTests{

	private Seguro createSUT(String compañia, String tipoSeguro, String fechaContrato, String fechaExpiracion, Integer recibidas, Integer vehiculo) {
		FacturaRecibida fr = null;
		Vehiculo v = null;
		
		if(recibidas != null && recibidas > 0) {
			fr = new FacturaRecibida();
		} if(vehiculo != null && vehiculo > 0) {
			v = new Vehiculo();
		}		
		Seguro seguro = new Seguro();
		seguro.setCompania(compañia);
		seguro.setTipoSeguro(tipoSeguro == null ? null : TipoSeguro.valueOf(tipoSeguro));
		seguro.setFechaContrato(fechaContrato == null ? null : LocalDate.parse(fechaContrato, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		seguro.setFechaExpiracion(fechaExpiracion == null ? null : LocalDate.parse(fechaExpiracion, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		seguro.setRecibidas(fr);
		seguro.setVehiculo(v);
		
		return seguro;
	}
	
	@ParameterizedTest
	@CsvSource({
		"Mutua, TODORRIESGO, 17/08/2018, 17/08/2022, 1, 1",
		"Linea Directa, TERCEROS, 12/09/2016, 17/08/2021, 1, 1"
	})
	void validateSeguroNoErrorsTest(String compañia, String tipoSeguro, String fechaContrato, String fechaExpiracion, Integer recibidas, Integer vehiculo){
		Seguro seguro = this.createSUT(compañia, tipoSeguro, fechaContrato, fechaExpiracion, recibidas, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Seguro>> constraintViolations = validator.validate(seguro);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"'', TODORRIESGO, 17/08/2018, 17/08/2022, 1, 1",
		"'    ', TERCEROS, 12/09/2016, 17/08/2021, 1, 1",
		", TERCEROS_AMPLIADO, 12/09/2016, 17/08/2021, 1, 1"
	})
	void validateSeguroCompaniaNotBlankTest(String compañia, String tipoSeguro, String fechaContrato, String fechaExpiracion, Integer recibidas, Integer vehiculo){
		Seguro seguro = this.createSUT(compañia, tipoSeguro, fechaContrato, fechaExpiracion, recibidas, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Seguro>> constraintViolations = validator.validate(seguro);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Mutua, , 17/08/2018, 17/08/2022, 1, 1",
		"Linea Directa, , 12/09/2016, 17/08/2021, 1, 1"
	})
	void validateSeguroTipoSeguroNotNullTest(String compañia, String tipoSeguro, String fechaContrato, String fechaExpiracion, Integer recibidas, Integer vehiculo){
		Seguro seguro = this.createSUT(compañia, tipoSeguro, fechaContrato, fechaExpiracion, recibidas, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Seguro>> constraintViolations = validator.validate(seguro);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
		
	@ParameterizedTest
	@CsvSource({
		"Mutua, TODORRIESGO, 17/08/2038, 17/08/2052, 1, 1",
		"Linea Directa, TERCEROS, 12/09/2056, 17/08/2021, 1, 1"
	})
	void validateSeguroFechaContratoPastOrPresentTest(String compañia, String tipoSeguro, String fechaContrato, String fechaExpiracion, Integer recibidas, Integer vehiculo){
		Seguro seguro = this.createSUT(compañia, tipoSeguro, fechaContrato, fechaExpiracion, recibidas, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Seguro>> constraintViolations = validator.validate(seguro);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Mutua, TODORRIESGO, 17/08/2018, 17/08/2022, , 1",
		"Linea Directa, TERCEROS, 12/09/2016, 17/08/2021, 0, 1"
	})
	void validateSeguroRecibidasNotNullTest(String compañia, String tipoSeguro, String fechaContrato, String fechaExpiracion, Integer recibidas, Integer vehiculo){
		Seguro seguro = this.createSUT(compañia, tipoSeguro, fechaContrato, fechaExpiracion, recibidas, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Seguro>> constraintViolations = validator.validate(seguro);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Mutua, TODORRIESGO, 17/08/2018, 17/08/2022, 1, 0",
		"Linea Directa, TERCEROS, 12/09/2016, 17/08/2021, 1, "
	})
	void validateSeguroVehiculoNotNullTest(String compañia, String tipoSeguro, String fechaContrato, String fechaExpiracion, Integer recibidas, Integer vehiculo){
		Seguro seguro = this.createSUT(compañia, tipoSeguro, fechaContrato, fechaExpiracion, recibidas, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Seguro>> constraintViolations = validator.validate(seguro);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}
