package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "almacenes")
public class Almacen extends BaseEntity{
	
	@NotBlank(message = "No puede ser vacio")
	@Size(max=50, min=3, message = "Debe ser de entre 3 y 50 caracteres")
	private String direccion;
	
	@NotNull(message = "No puede ser nulo")
	@Min(value=0, message = "Debe ser mayor o igual a cero")  
	private Double capacidad;
}
