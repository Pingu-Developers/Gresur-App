package org.springframework.gresur.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "recibidas")
public class FacturaRecibida extends Factura{
	
	@NotBlank
	private Concepto concepto;
	
	
}
