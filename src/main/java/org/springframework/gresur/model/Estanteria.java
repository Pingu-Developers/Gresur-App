package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "estanterias")
public class Estanteria extends BaseEntity{
	
	@NotBlank
	protected Categoria categoria;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	protected Double capacidad;

}
