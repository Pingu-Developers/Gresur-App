package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@OneToMany(mappedBy = "proveedor")
	private List<FacturaRecibida> recibidas;
	
}
