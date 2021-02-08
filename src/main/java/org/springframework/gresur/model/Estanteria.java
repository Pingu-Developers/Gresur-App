package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
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
	@NotNull(message = "No puede ser nulo")
	private Categoria categoria;
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 0, message = "Debe ser mayor o igual a cero")
	private Double capacidad;
	
	@JsonIgnore
	@NotNull(message = "No puede ser nulo")
	@ManyToOne(optional = false)
	private Almacen almacen;
}
