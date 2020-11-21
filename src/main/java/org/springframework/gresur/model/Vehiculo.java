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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity 
@Table(name="vehiculos")
public class Vehiculo extends BaseEntity {

	//FALTA AÑADIR REGLA DE NEGOCIO
	@NotBlank
	private String matricula;
	
	@Column(name = "URL_imagen")
	private String imagen;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	private Double capacidad; 
	
	@Pattern(regexp = "^[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*$")
	private String dimensiones;
	
	@NotNull
	private Boolean disponibilidad;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name ="tipo_vehiculo")
	private TipoVehiculo tipoVehiculo;
	
	@NotBlank
	@Min(value = 0, message = "debe ser mayor que cero")
	private Double MMA;
	
	@ManyToOne
	private Transportista transportista;
	
	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.REMOVE)
	private List<ITV> ITVs;
	
	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.REMOVE)
	private List<Seguro> seguros;
	
	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.REMOVE)
	private List<Reparacion> reparaciones;
	
	@OneToMany(mappedBy = "vehiculo")
	private List<Pedido> pedidos;
}