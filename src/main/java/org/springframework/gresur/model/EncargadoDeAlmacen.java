package org.springframework.gresur.model;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "encargadosAlmacen")
public class EncargadoDeAlmacen extends Personal{
	
	@JsonIgnore
	@NotNull
	@OneToOne(optional = false)
	private Almacen almacen;
}
