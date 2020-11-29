package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ContratoTests extends ValidatorTests{
	
	private Contrato createSUT(Double nomina, String entidadBancaria,String fechaInicio, String fechaFin,String tipoJornada,Integer personal) {
		Personal p = null;
		
		if(personal != null && personal > 0) {
			p = new Personal();
		}	
		Contrato contrato = new Contrato();
		contrato.setNomina(nomina);
		contrato.setEntidadBancaria(entidadBancaria);
		contrato.setFechaInicio(fechaInicio == null ? null : LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		contrato.setFechaFin(fechaFin == null ? null : LocalDate.parse(fechaFin, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		contrato.setTipoJornada(tipoJornada == null ? null : TipoJornada.valueOf(tipoJornada));
		contrato.setPersonal(p);
		return contrato;
	}
	
	@ParameterizedTest
	@CsvSource({
		"1500.50,La Caixa,21/10/2017,23/09/2026,COMPLETA,1",
		"1600.50,La Caixa,20/10/2016,21/12/2030,MEDIA_JORNADA,1",
		"1600.50,La Caixa,20/10/2016,21/10/2021,PARCIAL,1"
	})
	void validateContratoNoErrorsTest(Double nomina, String entidadBancaria,String fechaInicio, String fechaFin,String tipoJornada,Integer personal) {
		Contrato contrato = this.createSUT(nomina, entidadBancaria, fechaInicio, fechaFin, tipoJornada, personal);

		Validator validator = createValidator();
		Set<ConstraintViolation<Contrato>> constraintViolations = validator.validate(contrato);
		assertThat(constraintViolations.size()).isEqualTo(0);

	}
	
	@ParameterizedTest
	@CsvSource({
		"1500.50,La Caixa,22/10/2021,21/10/2021,COMPLETA,1",
		"1600.50,La Caixa,20/10/2022,21/12/2020,MEDIA_JORNADA,1",
		"1600.50,La Caixa,20/10/2023,21/10/2025,PARCIAL,1"
	})	
	void validateContratoFechaInicioIsFutureTest(Double nomina, String entidadBancaria,String fechaInicio, String fechaFin,String tipoJornada,Integer personal) {
		Contrato contrato = this.createSUT(nomina, entidadBancaria, fechaInicio, fechaFin, tipoJornada, personal);

		Validator validator = createValidator();
		Set<ConstraintViolation<Contrato>> constraintViolations = validator.validate(contrato);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1500.50,La Caixa,21/10/2017,21/10/2017,COMPLETA,1",
		"1600.50,La Caixa,20/10/2016,19/10/2016,MEDIA_JORNADA,1",
		"1600.50,La Caixa,20/10/2016,21/10/2015,PARCIAL,1"
	})
	void validateContratoFechaFinIsPastTest(Double nomina, String entidadBancaria,String fechaInicio, String fechaFin,String tipoJornada,Integer personal) {
		Contrato contrato = this.createSUT(nomina, entidadBancaria, fechaInicio, fechaFin, tipoJornada, personal);

		Validator validator = createValidator();
		Set<ConstraintViolation<Contrato>> constraintViolations = validator.validate(contrato);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1500.50,La Caixa,21/10/2017,21/10/2022,,1",
		"1600.50,La Caixa,20/10/2016,19/10/2023,,1",
		"1600.50,La Caixa,20/10/2016,21/10/2023,,1"
	})
	void validateContratoTipoJornadaNotNullTest(Double nomina, String entidadBancaria,String fechaInicio, String fechaFin,String tipoJornada,Integer personal) {
		Contrato contrato = this.createSUT(nomina, entidadBancaria, fechaInicio, fechaFin, tipoJornada, personal);

		Validator validator = createValidator();
		Set<ConstraintViolation<Contrato>> constraintViolations = validator.validate(contrato);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1500.50,La Caixa,21/10/2017,21/10/2022,COMPLETA,",
		"1600.50,La Caixa,20/10/2016,19/10/2023,MEDIA_JORNADA,",
		"1600.50,La Caixa,20/10/2016,21/10/2023,PARCIAL,"
	})
	void validateContratoNotPersonalTest(Double nomina, String entidadBancaria,String fechaInicio, String fechaFin,String tipoJornada,Integer personal) {
		Contrato contrato = this.createSUT(nomina, entidadBancaria, fechaInicio, fechaFin, tipoJornada, personal);

		Validator validator = createValidator();
		Set<ConstraintViolation<Contrato>> constraintViolations = validator.validate(contrato);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

}
