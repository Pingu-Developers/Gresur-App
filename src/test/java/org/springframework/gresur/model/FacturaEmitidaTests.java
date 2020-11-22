package org.springframework.gresur.model;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FacturaEmitidaTests extends ValidatorTests {
	
	private FacturaEmitida createSUT(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
		List<LineaFactura> lf = new ArrayList<LineaFactura>();
		Pedido p = null;
		Cliente c = null;
		Dependiente d = null;
		
		if(lineasFacturas != null && lineasFacturas > 0) {
			LineaFactura lf1 = new LineaFactura();
			LineaFactura lf2 = new LineaFactura();
			lf.add(lf1);
			lf.add(lf2);
		} if(pedido != null && pedido > 0) {
			p = new Pedido();
		} if(dependiente != null && dependiente > 0) {
			d = new Dependiente();
		} if(cliente!= null && cliente > 0) {
			c = new Cliente();
		}
		
		FacturaEmitida emitida = new FacturaEmitida();
		emitida.setFecha(fecha == null ? null : LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		emitida.setImporte(importe);
		emitida.setEstaPagada(pagada);
		emitida.setLineasFacturas(lf);
		emitida.setPedido(p);
		emitida.setDependiente(d);
		emitida.setCliente(c);
		return emitida;
	}
		
	@ParameterizedTest
	@CsvSource({
		"22/11/2020,40.3,true,0,0,1,1",
		"22/11/2021,40.3,false,1,0,1,1",
		"22/12/2020,4.3,true,0,1,1,1",
		"20/11/2021,40.3,false,0,0,1,1",
		"22/12/2020,400.3,true,0,0,1,1",
	})
	void validateFacturaEmitidaNoErrorsTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {	
		FacturaEmitida emi = this.createSUT(fecha, importe, pagada, lineasFacturas, pedido, dependiente, cliente);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaEmitida>> constraintViolations = validator.validate(emi);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	//TODO PREGUNTAR SOBRE VALIDACION @DateTimeFormat y @Null para la fecha 
//	@ParameterizedTest
//	@CsvSource({
//		"20/11/2021,40.3,false,0,0,1,0",
//		"22/12/2020,400.3,true,0,0,0,1",
//	})
//	void validateFacturaEmitidaDateTimeFormatTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
//		
//		FacturaEmitida facturaEmitida = this.createSUT(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente);
//		
//		Validator validator = createValidator();
//		Set<ConstraintViolation<FacturaEmitida>> constraintViolations = validator.validate(facturaEmitida);
//		assertThat(constraintViolations.size()).isEqualTo(1);
//	}
	
	@ParameterizedTest
	@CsvSource({
		"20/11/2021,,false,0,0,1,1",
		"22/12/2020,,true,1,1,1,1",
	})
	void validateFacturaEmitidaImporteNotNullTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
		
		FacturaEmitida facturaEmitida = this.createSUT(fecha, importe, pagada, lineasFacturas, pedido, dependiente, cliente);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaEmitida>> constraintViolations = validator.validate(facturaEmitida);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Min"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"20/11/2021,-43,false,0,0,1,1",
		"22/12/2020,-222,true,1,1,1,1",
	})
	void validateFacturaEmitidaImporteMinTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
		
		FacturaEmitida facturaEmitida = this.createSUT(fecha, importe, pagada, lineasFacturas, pedido, dependiente, cliente);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaEmitida>> constraintViolations = validator.validate(facturaEmitida);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotNull"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"20/11/2021,454,,0,0,1,1",
		"22/12/2020,212,,1,1,1,1",
	})
	void validateFacturaEmitidaEstaPagadaNotNullTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
		
		FacturaEmitida facturaEmitida = this.createSUT(fecha, importe, pagada, lineasFacturas, pedido, dependiente, cliente);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaEmitida>> constraintViolations = validator.validate(facturaEmitida);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"20/11/2021,454,true,0,0,0,1",
		"22/12/2020,212,false,1,1,,1",
	})
	void validateFacturaEmitidaDependienteNotNullTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
		
		FacturaEmitida facturaEmitida = this.createSUT(fecha, importe, pagada, lineasFacturas, pedido, dependiente, cliente);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaEmitida>> constraintViolations = validator.validate(facturaEmitida);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"20/11/2021,454,true,0,0,1,0",
		"22/12/2020,212,false,1,1,1,",
	})
	void validateFacturaEmitidaClienteNotNullTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
		
		FacturaEmitida facturaEmitida = this.createSUT(fecha, importe, pagada, lineasFacturas, pedido, dependiente, cliente);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaEmitida>> constraintViolations = validator.validate(facturaEmitida);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

}
