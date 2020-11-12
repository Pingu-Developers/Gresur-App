package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
@Table(name = "almacenes")
public class Almacen extends BaseEntity{
	
	@NotBlank
	protected String direccion;
	
	@NotBlank
	@Min(value=0, message="debe ser mayor o igual a cero")  
	protected Double capacidad;
}
