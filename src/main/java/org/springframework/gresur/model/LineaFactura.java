package org.springframework.gresur.model;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

public class LineaFactura {

	@Min(value = 1)
	private Integer cantidad;
	
	@ManyToOne(optional = true)
	Factura factura;
	
//	@ManyToOne(optional = true)
//	Producto producto;
	
}
