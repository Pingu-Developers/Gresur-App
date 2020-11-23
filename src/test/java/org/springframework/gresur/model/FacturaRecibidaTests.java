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

class FacturaRecibidaTests extends ValidatorTests{

	
	private FacturaRecibida createSUT(String fecha, Double importe, Boolean estaPagada, Integer lista, String concepto, Integer proveedor) {
		List<LineaFactura> lLineaFactura = new ArrayList<LineaFactura>();
		Proveedor p = null;
		
		if(lista!=null && lista>0) {
			LineaFactura lf = new LineaFactura();
			lLineaFactura.add(lf);
		} 
		
		if(proveedor!=null && proveedor>0) {
			p = new Proveedor();
		}
		
		FacturaRecibida facturaRecibida = new FacturaRecibida();
		facturaRecibida.setFecha(fecha == null ? null : LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaRecibida.setImporte(importe);
		facturaRecibida.setEstaPagada(estaPagada == null ? null : estaPagada);
		facturaRecibida.setLineasFacturas(lLineaFactura);
		facturaRecibida.setConcepto(concepto==null ? null : Concepto.valueOf(concepto));
		facturaRecibida.setProveedor(p);
		
		return facturaRecibida;
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"16/11/2019, 440.54, true, 1, OTROS, 0",
		"16/11/2019, 43.3, false, 0, GASTOS_VEHICULOS, 1"
	})
	void validateFacturaRecibidaNoErrorsTest(String fecha, Double importe, Boolean estaPagada, Integer lista, String concepto, Integer proveedor) {
		
		FacturaRecibida facturaRecibida = this.createSUT(fecha, importe, estaPagada, lista, concepto, proveedor);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaRecibida>> constraintViolations = validator.validate(facturaRecibida);
		
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"16/11/2019, , true, 1, GASTOS_VEHICULOS, 0",
		"17/11/2019, , false, 0, GASTOS_VEHICULOS, 1"
	})
	void validateFacturaRecibidaImporteNotNullTest(String fecha, Double importe, Boolean estaPagada, Integer lista, String concepto, Integer proveedor) {
		
		FacturaRecibida facturaRecibida = this.createSUT(fecha, importe, estaPagada, lista, concepto, proveedor);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaRecibida>> constraintViolations = validator.validate(facturaRecibida);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Min"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"16/11/2019, -44, true, 1, GASTOS_VEHICULOS, 0",
		"17/11/2019, -0.1, false, 0, GASTOS_VEHICULOS, 1"
	})
	void validateFacturaRecibidaImporteMinTest(String fecha, Double importe, Boolean estaPagada, Integer lista, String concepto, Integer proveedor) {
		
		FacturaRecibida facturaRecibida = this.createSUT(fecha, importe, estaPagada, lista, concepto, proveedor);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaRecibida>> constraintViolations = validator.validate(facturaRecibida);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotNull"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"16/11/2019, 444, , 1, REPOSICION_STOCK, 0",
		"17/11/2019, 332, , 0, GASTOS_VEHICULOS, 1"
	})
	void validateFacturaRecibidaEstaPagadaNotNullTest(String fecha, Double importe, Boolean estaPagada, Integer lista, String concepto, Integer proveedor) {
		
		FacturaRecibida facturaRecibida = this.createSUT(fecha, importe, estaPagada, lista, concepto, proveedor);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaRecibida>> constraintViolations = validator.validate(facturaRecibida);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"16/11/2019, 440.54, true, 1, , 0",
		"16/11/2019, 43.3, false, 0, , 1"
	})
	void validateFacturaRecibidaConceptoNotNullTest(String fecha, Double importe, Boolean estaPagada, Integer lista, String concepto, Integer proveedor) {
		
		FacturaRecibida facturaRecibida = this.createSUT(fecha, importe, estaPagada, lista, concepto, proveedor);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaRecibida>> constraintViolations = validator.validate(facturaRecibida);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

}
