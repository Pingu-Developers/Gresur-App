package org.springframework.gresur.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity 
@Table(name="vehiculos")
public class Vehiculo extends BaseEntity {
	
	@NotBlank
	@Column(unique = true)
	private String matricula;
	
	@Column(name = "URL_imagen")
	private String imagen;
	
	@NotNull
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	private Double capacidad; 
	
	@NotNull
	private Boolean disponibilidad;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name ="tipo_vehiculo")
	private TipoVehiculo tipoVehiculo;
	
	@NotNull
	@Min(value = 0, message = "debe ser mayor que cero")
	private Double MMA;	
}