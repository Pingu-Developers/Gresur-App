package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "lineas_factura", uniqueConstraints = @UniqueConstraint(columnNames = {"factura_id", "producto_id"}))
public class LineaFactura extends BaseEntity {

	@NotNull
	@Min(value = 0)
	private Integer cantidad;
	
	@NotNull
	@PositiveOrZero
	private Double precio;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "factura_id")
	private Factura factura;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "producto_id")
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
		if (factura.id == null) {
			if (other.factura.id != null)
				return false;
		} else if (!factura.id.equals(other.factura.id))
			return false;
		if (producto.id == null) {
			if (other.producto.id != null)
				return false;
		} else if (!producto.id.equals(other.producto.id))
			return false;
		return true;
	}
}
