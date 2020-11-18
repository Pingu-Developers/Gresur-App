package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "emitidas")
public class FacturaEmitida extends Factura{
	
	@OneToOne
	Pedido pedido;
	
	@ManyToOne(optional = false)
	Dependiente dependiente;
	
	@ManyToOne(optional = false)
	Cliente cliente;
	
}
