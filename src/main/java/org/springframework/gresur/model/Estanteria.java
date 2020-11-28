package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estanterias")
public class Estanteria extends BaseEntity{
	
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Categoria categoria;
	
	@NotNull
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	private Double capacidad;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	private Almacen almacen;
	
	@JsonIgnore
	@OneToMany(mappedBy = "estanteria")
	private List<Producto> productos;
}
