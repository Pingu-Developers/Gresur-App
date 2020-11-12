package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "emitidas")
public class FacturaEmitida extends Factura{
	
	@OneToOne(optional = true)
	Pedido pedido;
	
	@ManyToOne(optional = false)
	Dependiente dependiente;
	
	@ManyToOne(optional = false)
	Cliente cliente;
	
}
