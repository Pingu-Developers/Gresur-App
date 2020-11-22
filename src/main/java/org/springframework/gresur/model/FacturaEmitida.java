package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "emitidas")
public class FacturaEmitida extends Factura{
	
	@OneToOne
	private Pedido pedido;
	
	@NotNull //TODO Añadido NotNull para validar los test unitarios
	@ManyToOne(optional = false)
	private Dependiente dependiente;
	
	@NotNull //TODO Añadido NotNull para validar los test unitarios
	@ManyToOne(optional = false)
	private Cliente cliente;
	
}
