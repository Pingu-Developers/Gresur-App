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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "pedidos")
public class Pedido extends BaseEntity{

	@NotBlank(message = "No puede ser vacio")
	@Column(name = "direccion_envio")
	private String direccionEnvio;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "No puede ser nulo")
	@Column(name = "estado")
	private EstadoPedido estado;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "fecha_realizacion")
	private LocalDate fechaRealizacion;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "fecha_envio")
	private LocalDate fechaEnvio;
	
	@NotNull(message = "No puede ser nulo")
	@OneToOne(optional = false)
	@JoinColumn(name = "factura_emitida_id")
	private FacturaEmitida facturaEmitida;
	
	@ManyToOne
	@JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;
	
	@ManyToOne
	@JoinColumn(name = "transportista_id")
	private Transportista transportista;
	
	/* Propiedad derivada */
	public Boolean recogeEnTienda() {
		return this.getDireccionEnvio() == "C/ Ligastorro nº 9" || this.getDireccionEnvio() == "Avenida Gresur edificio AG";
	}
		
	public FacturaEmitida getFacturaEmitida() {
		return (FacturaEmitida) this.facturaEmitida.getDefinitiva();
	}
}
