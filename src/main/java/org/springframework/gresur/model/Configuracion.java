package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "config")
public class Configuracion extends BaseEntity{
	
	@NotNull
	@Column(name = "salario_minimo")
	private Double salarioMinimo;
	
	@NotNull
	@Column(name = "num_max_notificaciones")
	private Integer numMaxNotificaciones;
	
	@NotNull
	private Double MMA;
}
