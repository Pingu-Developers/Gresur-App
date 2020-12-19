package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ITVTests extends ValidatorTests {

	private ITV createSUT(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		
		Vehiculo veh = null;
		FacturaRecibida recibida = null;
		
		ITV itv = new ITV();
		
		itv.setFecha(fecha == null ? null : LocalDate.parse(fecha,DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		itv.setExpiracion(fechaExpiracion == null ? null : LocalDate.parse(fechaExpiracion,DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		itv.setResultado(resultadoITV == null ? null : ResultadoITV.valueOf(resultadoITV));
		
		if(facturaRecibida!=null && facturaRecibida>0) {
			recibida = new FacturaRecibida();
			itv.setRecibidas(recibida);
		}
		
		if(vehiculo != null && vehiculo>0) {
			veh = new Vehiculo();
			itv.setVehiculo(veh);
		}
		
		return itv;
	}
	
	/*LOS ATRIBUTOS VEHICULO Y FACTURA_RECIBIDA SI NO APARECEN O BIEN VALEN 0, SIGNIFICAN QUE SON NULOS */
	@ParameterizedTest
	@CsvSource({
		"21/10/2019,21/10/2020,FAVORABLE,1,1",
		"10/01/2018,16/01/2019,DESFAVORABLE,1,1",
		"11/01/2017,11/01/2018,NEGATIVA,1,1"
	})
	void validateITVNoErrorsTest(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		ITV itv = this.createSUT(fecha, fechaExpiracion, resultadoITV, facturaRecibida, vehiculo);
		Validator validator = createValidator();
		Set<ConstraintViolation<ITV>> constraintViolations = validator.validate(itv);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		",21/10/2022,FAVORABLE,1,1",
		",16/01/2022,DESFAVORABLE,1,1"
	})
	void validateITVFechaNotNullTest(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		ITV itv = this.createSUT(fecha, fechaExpiracion, resultadoITV, facturaRecibida, vehiculo);
		Validator validator = createValidator();
		Set<ConstraintViolation<ITV>> constraintViolations = validator.validate(itv);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2023,21/10/2022,FAVORABLE,1,1",
		"16/01/2022,16/01/2022,DESFAVORABLE,1,1",
		"11/01/2022,11/01/2021,NEGATIVA,1,1"
	})
	void validateITVFechaIsFutureTest(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		ITV itv = this.createSUT(fecha, fechaExpiracion, resultadoITV, facturaRecibida, vehiculo);
		Validator validator = createValidator();
		Set<ConstraintViolation<ITV>> constraintViolations = validator.validate(itv);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2019,,FAVORABLE,1,1",
		"16/01/2018,,DESFAVORABLE,1,1"
	})
	void validateITVExpiracionNotNullTest(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		ITV itv = this.createSUT(fecha, fechaExpiracion, resultadoITV, facturaRecibida, vehiculo);
		Validator validator = createValidator();
		Set<ConstraintViolation<ITV>> constraintViolations = validator.validate(itv);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2018,21/10/2019,,1,1",
		"16/01/2019,16/01/2020,,1,1",
		"11/01/2020,11/01/2021,,1,1"
	})
	void validateITVResultadoITVNotNullTest(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		ITV itv = this.createSUT(fecha, fechaExpiracion, resultadoITV, facturaRecibida, vehiculo);
		Validator validator = createValidator();
		Set<ConstraintViolation<ITV>> constraintViolations = validator.validate(itv);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	@ParameterizedTest
	@CsvSource({
		"21/10/2018,21/10/2019,FAVORABLE,,1",
		"16/01/2019,16/01/2020,DESFAVORABLE,,1",
		"11/01/2020,11/01/2021,NEGATIVA,,1"
	})
	void validateITVFacturasRecibidasNotNullTest(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		ITV itv = this.createSUT(fecha, fechaExpiracion, resultadoITV, facturaRecibida, vehiculo);
		Validator validator = createValidator();
		Set<ConstraintViolation<ITV>> constraintViolations = validator.validate(itv);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2018,21/10/2019,FAVORABLE,1,",
		"16/01/2019,16/01/2020,DESFAVORABLE,1,",
		"11/01/2020,11/01/2021,NEGATIVA,1,"
	})
	void validateITVVehiculoNotNullTest(String fecha, String fechaExpiracion, String resultadoITV,Long facturaRecibida, Long vehiculo) {
		ITV itv = this.createSUT(fecha, fechaExpiracion, resultadoITV, facturaRecibida, vehiculo);
		Validator validator = createValidator();
		Set<ConstraintViolation<ITV>> constraintViolations = validator.validate(itv);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	
}
