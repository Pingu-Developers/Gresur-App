package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estanterias")
public class Estanteria extends BaseEntity{
	
	@NotBlank
	protected Categoria categoria;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	protected Double capacidad;
	
	@ManyToOne(optional = false)
	protected Almacen almacen;
	
	@OneToMany(mappedBy = "estanteria")
	protected List<Producto> productos;
}
