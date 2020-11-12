package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido extends BaseEntity{

	@NotBlank
	private String direccionEnvio;
	@NotBlank
	private Estado estado;
}
