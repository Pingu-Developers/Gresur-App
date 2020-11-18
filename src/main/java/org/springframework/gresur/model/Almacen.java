package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "almacenes")
public class Almacen extends BaseEntity{
	
	@NotBlank
	protected String direccion;
	
	@NotBlank
	@Min(value=0, message = "debe ser mayor o igual a cero")  
	protected Double capacidad;
	
	@OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL)
	protected List<Estanteria> estanterias;
	
	@OneToOne(mappedBy = "almacen", optional = false)
	protected EncargadoDeAlmacen encargado;
}
