package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PedidoTests extends ValidatorTests{

	private Pedido createSUT(String direccionEnvio, String estado, String fechaEnvio, Integer facturaEmitida, Integer vehiculo) {
		
		FacturaEmitida f = null;
		Vehiculo v = null;
		
		if(facturaEmitida!=null && facturaEmitida>0) {
			f = new FacturaEmitida();
		}
		
		if(vehiculo!=null && vehiculo>0) {
			v = new Vehiculo();
		}
		
		Pedido pedido = new Pedido();
		pedido.setDireccionEnvio(direccionEnvio);
		pedido.setEstado(estado == null ? null : Estado.valueOf(estado));
		pedido.setFechaEnvio(fechaEnvio == null ? null : LocalDate.parse(fechaEnvio, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedido.setFacturaEmitida(f);
		pedido.setVehiculo(v);
		
		return pedido;
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro, ABIERTO, 20/02/2020, 3, 5",
		"C/Diseño y pruebas, CERRADO, 20/04/2020, 2, 8",
	})
	void validatePedidoNoErrorsTest(String direccionEnvio, String estado, String fechaEnvio, Integer facturaEmitida, Integer vehiculo) {
		
		Pedido pedido = this.createSUT(direccionEnvio, estado, fechaEnvio, facturaEmitida, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Pedido>> constraintViolations = validator.validate(pedido);
		
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		", ABIERTO, 20/02/2020, 3, 5",
		"'', CERRADO, 20/04/2020, 2, 8",
		"'         ', CERRADO, 20/04/2020, 2, 8"
	})
	void validatePedidoDireccionEnvioNotBlankTest(String direccionEnvio, String estado, String fechaEnvio, Integer facturaEmitida, Integer vehiculo) {
		
		Pedido pedido = this.createSUT(direccionEnvio, estado, fechaEnvio, facturaEmitida, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Pedido>> constraintViolations = validator.validate(pedido);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro,, 20/02/2020, 3, 5",
		"C/Diseño y pruebas,, 20/04/2020, 2, 8",
	})
	void validatePedidoEstadoNotNullTest(String direccionEnvio, String estado, String fechaEnvio, Integer facturaEmitida, Integer vehiculo) {
		
		Pedido pedido = this.createSUT(direccionEnvio, estado, fechaEnvio, facturaEmitida, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Pedido>> constraintViolations = validator.validate(pedido);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro, ABIERTO, 20/02/2020, 0, 5",
		"C/Diseño y pruebas, CERRADO, 20/04/2020, , 8",
	})
	void validatePedidoFacturaEmitidaNotNullTest(String direccionEnvio, String estado, String fechaEnvio, Integer facturaEmitida, Integer vehiculo) {
		
		Pedido pedido = this.createSUT(direccionEnvio, estado, fechaEnvio, facturaEmitida, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Pedido>> constraintViolations = validator.validate(pedido);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"C/Ligastorro, ABIERTO, 20/02/2020, 3, 0",
		"C/Diseño y pruebas, CERRADO, 20/04/2020, 2, ",
	})
	void validatePedidoVehiculoNotNullTest(String direccionEnvio, String estado, String fechaEnvio, Integer facturaEmitida, Integer vehiculo) {
		
		Pedido pedido = this.createSUT(direccionEnvio, estado, fechaEnvio, facturaEmitida, vehiculo);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Pedido>> constraintViolations = validator.validate(pedido);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
}