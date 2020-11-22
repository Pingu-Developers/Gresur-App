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

	@ParameterizedTest
	@CsvSource({
		"22/11/2020,40.3,True,0,0,0,0",
		"22/11/2021,40.3,False,1,0,0,0",
		"22/12/2020,4.3,True,0,1,0,0",
		"20/11/2021,40.3,False,0,0,1,0",
		"22/12/2020,400.3,True,0,0,0,1",

	})
	void validateFacturaEmitidaNoErrorsTest(String fecha,Double importe,Boolean pagada,Integer lineasFacturas,Integer pedido,Integer dependiente,Integer cliente) {
		
		FacturaEmitida emi = new FacturaEmitida();
		emi.setFecha(LocalDate.parse(fecha,DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		emi.setImporte(importe);
		emi.setEstaPagada(pagada);	
		if(lineasFacturas == 0) {
			List<LineaFactura> l = new ArrayList<>();
			emi.setLineasFacturas(l);
		}else if(lineasFacturas == 1) {
			List<LineaFactura> l = new ArrayList<>();
			l.add(new LineaFactura());
			emi.setLineasFacturas(l);
		}
		 if(pedido == 1) {
			emi.setPedido(new Pedido());
		}
		if(dependiente == 1) {
			emi.setDependiente(new Dependiente());
		}
		if(cliente == 1) {
			emi.setCliente(new Cliente());
		}	
		Validator validator = createValidator();
		Set<ConstraintViolation<FacturaEmitida>> constraintViolations =
				validator.validate(emi);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}

}
