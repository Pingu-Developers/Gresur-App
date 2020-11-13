package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "config")
public class Configuracion extends BaseEntity{
	
	@NotNull
	private Double salarioMinimo;
	
	@NotNull
	private Integer numMaxNotificaciones;
	
	@NotNull
	private Double MMA;
}
