package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "pedidos")
public class Pedido extends BaseEntity{

	@NotBlank
	private String direccionEnvio;
	
	@NotBlank
	private Estado estado;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaEnvio;
	
	@OneToOne(mappedBy = "pedido", optional = false)
	private FacturaEmitida facturaEmitida;
	
	@ManyToOne(optional = false)
	private Vehiculo vehiculo;
}
