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

@Data
@Entity 
@Table(name="vehiculos")
public class Vehiculo extends BaseEntity {

	//FALTA AÑADIR REGLA DE NEGOCIO
	@NotBlank
	protected String matricula;
	
	@Column(name = "URL_imagen")
	protected String imagen;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	protected Double capacidad; 
	
	//FALTA AÑADIR PATRON PARA DIMENSION
	protected String dimensiones;
	
	@NotNull
	protected Boolean disponibilidad;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name ="tipo_vehiculo")
	protected TipoVehiculo tipoVehiculo;
	
	@NotBlank
	@Min(value = 0, message = "debe ser mayor que cero")
	protected Double MMA;
}
