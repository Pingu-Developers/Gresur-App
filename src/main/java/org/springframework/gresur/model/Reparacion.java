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

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reparaciones")
public class Reparacion extends BaseEntity implements Comparable<Reparacion>{

	@NotNull(message = "No puede ser nulo")
	@PastOrPresent(message = "La fecha debe ser pasada o presente")
	@Column(name = "fecha_entrada_taller")
	protected LocalDate fechaEntradaTaller;
	
	@Column(name = "fecha_salida_taller")
	protected LocalDate fechaSalidaTaller;
	
	@Lob
	protected String descripcion;
	
	@JsonView
	@NotNull(message = "No puede ser nulo")
	@OneToOne(optional = false)
	@JoinColumn(name = "factura_recibida_id")
	private FacturaRecibida recibidas;
	
	@JsonView
	@NotNull(message = "No puede ser nulo")
	@ManyToOne(optional = false)
	@JoinColumn(name= "vehiculo_id")
	private Vehiculo vehiculo;

	@Override
	public int compareTo(Reparacion o) {
		if(o.fechaSalidaTaller == null && o.fechaSalidaTaller == null)
			return this.fechaEntradaTaller.compareTo(o.fechaEntradaTaller);
		else if(this.fechaSalidaTaller == null)
			return -1;
		else if (o.fechaSalidaTaller == null)
			return 1;
		else {
			int ret = this.fechaSalidaTaller.compareTo(o.fechaSalidaTaller);
			return ret == 0 ? this.fechaEntradaTaller.compareTo(o.fechaEntradaTaller) : ret ;
		}
	}
}
