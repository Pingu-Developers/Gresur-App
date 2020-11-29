package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "proveedores")
public class Proveedor extends Entidad {
	
	@NotBlank
	@Pattern(regexp = "^([a-zA-Z]{2})\\s*\\t*(\\d{2})\\s*\\t*(\\d{4})\\s*\\t*(\\d{4})\\s*\\t*(\\d{2})\\s*\\t*(\\d{10})$" )
	private String IBAN;
	
}
