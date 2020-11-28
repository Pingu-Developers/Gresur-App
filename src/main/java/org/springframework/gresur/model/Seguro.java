package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "seguros")
public class Seguro extends BaseEntity implements Comparable<Seguro>{
	
	@NotBlank
	private String compania;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	private TipoSeguro tipoSeguro;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@PastOrPresent
	private LocalDate fechaContrato;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaExpiracion;
	
	@JsonIgnore
	@NotNull
	@OneToOne(optional = false)
	@JoinColumn(name = "facturas_recibidas")
	private FacturaRecibida recibidas;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	private Vehiculo vehiculo;

	@Override
	public int compareTo(Seguro o) {
		return this.fechaContrato.compareTo(o.fechaContrato);
	}
}
