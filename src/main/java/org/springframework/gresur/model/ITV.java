package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity 
@Table(name="ITV")
public class ITV extends BaseEntity{
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@PastOrPresent
	private LocalDate fecha;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate expiracion;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	private ResultadoITV resultado;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "facturas_recibidas")
	private FacturaRecibida recibidas;
	
	@ManyToOne
	private Vehiculo vehiculo;
}
