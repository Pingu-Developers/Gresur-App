package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reparaciones")
public class Reparacion extends BaseEntity{

	@NotNull
	@PastOrPresent
	@Column(name = "fecha_entrada_taller")
	protected LocalDate fechaEntradaTaller;
	
	@Column(name = "fecha_salida_taller")
	protected LocalDate fechaSalidaTaller;
	
	@Lob
	protected String descripcion;
	
	@JsonIgnore
	@NotNull
	@OneToOne(optional = false)
	@JoinColumn(name = "factura_recibida_id")
	private FacturaRecibida recibidas;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name= "vehiculo_id")
	private Vehiculo vehiculo;
}
