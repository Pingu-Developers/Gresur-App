package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@ManyToOne
	private Transportista transportista;
	
	@JsonIgnore
	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.REMOVE)
	private List<ITV> ITVs;
	
	@JsonIgnore
	@Size(min = 1)
	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.REMOVE)
	private List<Seguro> seguros;
	
	@JsonIgnore
	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.REMOVE)
	private List<Reparacion> reparaciones;
	
	@JsonIgnore
	@OneToMany(mappedBy = "vehiculo")
	private List<Pedido> pedidos;
}

//TODO RN: VALIDACION DE FECHAFINAL >= FECHAINICIAL EN EL SERVICE (ITV; SEGURO; REPARACION)
