package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "config")
public class Configuracion extends BaseEntity{
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "salario_minimo")
	private Double salarioMinimo;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "num_max_notificaciones")
	private Integer numMaxNotificaciones;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "factura_emitida_seq")
	private Long facturaEmitidaSeq;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "factura_recibida_seq")
	private Long facturaRecibidaSeq;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "factura_emitida_rect_seq")
	private Long facturaEmitidaRectSeq;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "factura_recibida_rect_seq")
	private Long facturaRecibidaRectSeq;
}
