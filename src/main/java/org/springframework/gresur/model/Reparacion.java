package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reparaciones")
public class Reparacion extends BaseEntity{

	@PastOrPresent
	protected LocalDate fechaEntradaTaller;
	
	@NotNull
	protected LocalDate fechaSalidaTaller;
	
	@JsonIgnore
	@NotNull
	@OneToOne(optional = false)
	@JoinColumn(name = "facturas_recibidas")
	private FacturaRecibida recibidas;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	private Vehiculo vehiculo;
}
