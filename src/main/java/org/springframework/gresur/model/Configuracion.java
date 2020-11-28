package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "config")
public class Configuracion extends BaseEntity{
	
	@NotNull
	@Column(name = "salario_minimo")
	private Double salarioMinimo;
	
	@NotNull
	@Column(name = "num_max_notificaciones")
	private Integer numMaxNotificaciones;
}
