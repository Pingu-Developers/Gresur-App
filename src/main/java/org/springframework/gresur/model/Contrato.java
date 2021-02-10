package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contratos")
public class Contrato extends BaseEntity{
	
	@NotNull(message = "No puede ser nulo")
	private Double nomina;
	
	@NotBlank(message = "No puede ser vacio")
	@Column(name = "entidad_bancaria")
	private String entidadBancaria;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "fecha_inicio")
	@PastOrPresent(message = "La fecha debe ser pasada o presente")
	private LocalDate fechaInicio;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "fecha_fin")
	@FutureOrPresent(message = "La fecha debe ser presente o futura")
	private LocalDate fechaFin;
	
	@NotNull(message = "No puede ser nulo")
	@Enumerated(value = EnumType.STRING)
	@Column(name ="tipo_jornada")
	private TipoJornada tipoJornada;
	
	@Lob
	private String observaciones;

	@NotNull(message = "No puede ser nulo")
	@ManyToOne(optional = false)
	private Personal personal;
}
