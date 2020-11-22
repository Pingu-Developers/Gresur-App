package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estanterias")
public class Estanteria extends BaseEntity{
	
	@NotNull
	private Categoria categoria;
	
	@NotNull
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	private Double capacidad;
	
	@NotNull //TODO para poder validar los tests unitarios
	@ManyToOne(optional = false)
	private Almacen almacen;
	
	@OneToMany(mappedBy = "estanteria")
	private List<Producto> productos;
}
