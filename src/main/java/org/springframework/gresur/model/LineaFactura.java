package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "lineas_factura", uniqueConstraints = @UniqueConstraint(columnNames = {"factura", "producto"}))
public class LineaFactura extends BaseEntity {

	@Min(value = 1)
	private Integer cantidad;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "factura")
	private Factura factura;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "producto")
	private Producto producto;
}
