package org.springframework.gresur.model;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "encargados_almacen")
public class EncargadoDeAlmacen extends Personal{
	
	//@NotNull(message = "No puede ser nulo")
	@OneToOne(optional = true)
	private Almacen almacen;
}
