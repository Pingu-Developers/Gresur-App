package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
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
	
	@NotBlank
	@Size(max=50, min=3)
	private String direccion;
	
	@NotNull
	@Min(value=0, message = "debe ser mayor o igual a cero")  
	private Double capacidad;
	
	@OneToOne(mappedBy = "almacen", optional = false)
	private EncargadoDeAlmacen encargado;
}
