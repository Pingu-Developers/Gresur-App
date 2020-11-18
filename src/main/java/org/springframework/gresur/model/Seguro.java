package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "seguros")
public class Seguro extends BaseEntity{
	
	@NotBlank
	protected String compania;
	
	protected TipoSeguro tipoSeguro;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@PastOrPresent
	protected LocalDate fechaContrato;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fechaExpiracion;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "facturas_recibidas")
	private FacturaRecibida recibidas;
	
	@ManyToOne(optional = false)
	private Vehiculo vehiculo;
}
