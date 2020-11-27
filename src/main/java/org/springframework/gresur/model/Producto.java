package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "productos")
public class Producto extends BaseEntity{
	
	@NotBlank
	private String nombre;
	
	private String descripcion;
	
	@NotBlank
	private String unidad;
	
	@NotNull
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	private Integer stock;
	
	//TODO NOTIFICACION CUANDO EL STOCK SEA MENOR QUE EL STOCK DE SEGURIDAD
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	@Column(name = "stock_seguridad")
	private Integer stockSeguridad;
	
	@Column(name = "URL_imagen")
	private String URLImagen;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	@Column(name = "precio_venta")
	private Double precioVenta;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	@Column(name = "precio_compra")
	private Double precioCompra;
	
	@Pattern(regexp = "^[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*$")
	private String dimensiones;
	
	@Positive(message = "el peso no puede ser negativo y debe ser mayor que 0")
	private Double pesoUnitario;
	
	@JsonIgnore
	@ManyToOne
	private Estanteria estanteria;
	
}
