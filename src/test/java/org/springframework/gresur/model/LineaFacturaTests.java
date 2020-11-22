package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LineaFacturaTests extends ValidatorTests {
	
	private LineaFactura createSUT(Integer cantidad,Long factura, Long producto) {
		
		LineaFactura lineaFactura = new LineaFactura();
		Factura fac = null;
		Producto prod = null;
		
		lineaFactura.setCantidad(cantidad);
		
		if(factura!=null && factura>0) {
			fac = new Factura();
			lineaFactura.setFactura(fac);
		}
		
		if(producto!=null && producto>0) {
			prod = new Producto();
			lineaFactura.setProducto(prod);
		}
		
		return lineaFactura;
	}
	/*LOS ATRIBUTOS FACTURA Y PRODUCTO SI NO APARECEN O BIEN VALEN 0, SIGNIFICAN QUE SON NULOS */
	@ParameterizedTest
	@CsvSource({
		"15,1,1",
		"8,1,1",
		"1,1,1"
	})
	void validateLineaFacturaNoErrorsTest(Integer cantidad,Long factura, Long producto) {
		LineaFactura lineaFactura = this.createSUT(cantidad, factura, producto);
		Validator validator = createValidator();
		Set<ConstraintViolation<LineaFactura>> constraintViolations = validator.validate(lineaFactura);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	@ParameterizedTest
	@CsvSource({
		"0,1,1",
		"-1,1,1",
		"0,1,1"
	})
	void validateLineaFacturaCantidadNotMinTest(Integer cantidad,Long factura, Long producto) {
		LineaFactura lineaFactura = this.createSUT(cantidad, factura, producto);
		Validator validator = createValidator();
		Set<ConstraintViolation<LineaFactura>> constraintViolations = validator.validate(lineaFactura);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	@ParameterizedTest
	@CsvSource({
		"15,,1",
		"8,0,1",
		"1,,1"
	})
	void validateLineaFacturaFacturaNotNullTest(Integer cantidad,Long factura, Long producto) {
		LineaFactura lineaFactura = this.createSUT(cantidad, factura, producto);
		Validator validator = createValidator();
		Set<ConstraintViolation<LineaFactura>> constraintViolations = validator.validate(lineaFactura);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	@ParameterizedTest
	@CsvSource({
		"15,1,",
		"8,1,",
		"1,1,0"
	})
	void validateLineaFacturaProductoNotNullTest(Integer cantidad,Long factura, Long producto) {
		LineaFactura lineaFactura = this.createSUT(cantidad, factura, producto);
		Validator validator = createValidator();
		Set<ConstraintViolation<LineaFactura>> constraintViolations = validator.validate(lineaFactura);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

}
