package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Estado estado;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaEnvio;
	
	@NotNull //TODO AÑADIDO NotNull para Tests
	@OneToOne(mappedBy = "pedido", optional = false)
	private FacturaEmitida facturaEmitida;
	
	@NotNull //TODO AÑADIDO NotNull para Testss
	@ManyToOne(optional = false)
	private Vehiculo vehiculo;
}
