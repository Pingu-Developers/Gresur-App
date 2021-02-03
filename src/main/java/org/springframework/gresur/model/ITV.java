package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity 
@Table(name="ITV")
public class ITV extends BaseEntity implements Comparable<ITV>{
	
	@NotNull
	@PastOrPresent
	@Column(name = "fecha")
	private LocalDate fecha;

	@NotNull
	@Column(name = "expiracion")
	private LocalDate expiracion;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "resultado")
	private ResultadoITV resultado;
	
	@JsonView
	@NotNull
	@OneToOne(optional = false)
	@JoinColumn(name = "factura_recibida_id")
	private FacturaRecibida recibidas;
	
	@JsonView
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;

	@Override
	public int compareTo(ITV o) {
		return this.getFecha().compareTo(o.getFecha());
	}
}
