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
	private String direccion;
	
	//TODO RN LA CAPACIDAD DE LAS ESTANTERIAS NO DEBE SUPERAR AL ALMACEN
	@NotBlank
	@Min(value=0, message = "debe ser mayor o igual a cero")  
	private Double capacidad;
	
	@OneToMany(mappedBy = "almacen", cascade = CascadeType.REMOVE)
	private List<Estanteria> estanterias;
	
	@OneToOne(mappedBy = "almacen", optional = false)
	private EncargadoDeAlmacen encargado;
}
