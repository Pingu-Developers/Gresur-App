package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class LineaFactura extends BaseEntity {

	@Min(value = 1)
	private Integer cantidad;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "factura_id")
	Factura factura;
	
//	@ManyToOne(optional = true)
//	Producto producto;
	
}
