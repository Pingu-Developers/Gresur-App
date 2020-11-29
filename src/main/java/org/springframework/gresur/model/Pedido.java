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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@NotNull
	private LocalDate fechaEnvio;
	
	@JsonIgnore
	@NotNull
	@OneToOne(mappedBy = "pedido", optional = false)
	private FacturaEmitida facturaEmitida;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	private Vehiculo vehiculo;
}
