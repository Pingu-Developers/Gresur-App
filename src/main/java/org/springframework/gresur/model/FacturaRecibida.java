package org.springframework.gresur.model;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "recibidas")
public class FacturaRecibida extends Factura{
	
	@NotNull
	private Concepto concepto;
	
	@ManyToOne
	private Proveedor proveedor;
}
