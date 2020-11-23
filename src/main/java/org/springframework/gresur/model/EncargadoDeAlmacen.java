package org.springframework.gresur.model;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "encargadosAlmacen")
public class EncargadoDeAlmacen extends Personal{
	
	@NotNull //TODO necesario para validar test unitario
	@OneToOne(optional = false)
	private Almacen almacen;
}
