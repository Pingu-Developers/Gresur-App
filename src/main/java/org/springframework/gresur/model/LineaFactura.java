package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "lineas_factura", uniqueConstraints = @UniqueConstraint(columnNames = {"factura", "producto"}))
public class LineaFactura extends BaseEntity {

	@Min(value = 1)
	private Integer cantidad;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "factura")
	private Factura factura;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "producto")
	private Producto producto;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((factura == null) ? 0 : factura.hashCode());
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaFactura other = (LineaFactura) obj;
		if (factura == null) {
			if (other.factura != null)
				return false;
		} else if (!factura.equals(other.factura))
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		return true;
	}
}
