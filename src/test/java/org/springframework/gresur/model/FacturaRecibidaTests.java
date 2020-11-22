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

	@ParameterizedTest
	@CsvSource({
		"16/11/2019, 440.54, true, 1, 0, 0",
		"16/11/2019, 43.3, false, 0, 1, 1"
	})
	void validateFacturaRecibidaNoErrorsTest(String fecha, Double importe, String estaPagada, Integer lista, Integer concepto, Integer proveedor) {
		
		FacturaRecibida facturaRecibida = new FacturaRecibida();
		facturaRecibida.setFecha(LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaRecibida.setImporte(importe);
		facturaRecibida.setEstaPagada(Boolean.parseBoolean(estaPagada));
		
		System.out.println(facturaRecibida);
		
		if(lista==0) {
			facturaRecibida.setLineasFacturas(new ArrayList<LineaFactura>());
		} else if(lista==1) {
			LineaFactura lf = new LineaFactura();
			List<LineaFactura> lLineaFactura = new ArrayList<LineaFactura>();
			lLineaFactura.add(lf);
			facturaRecibida.setLineasFacturas(new ArrayList<LineaFactura>());
		}
		
		if(concepto==0) {
			facturaRecibida.setConcepto(Concepto.PAGO_IMPUESTOS);
		} else if (concepto==1) {
			facturaRecibida.setConcepto(Concepto.REPOSICION_STOCK);
		}
		
		if(proveedor==0) {
			facturaRecibida.setProveedor(null);
		} else if(proveedor==1) {
			facturaRecibida.setProveedor(new Proveedor());
		}
				
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaRecibida>> constraintViolations = validator.validate(facturaRecibida);
		
		assertThat(constraintViolations.size()).isEqualTo(0);
		
	}

}
