package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "productos")
public class Producto extends BaseEntity{
	
	@NotBlank(message = "No puede ser vacio")
	@Column(unique = true)
	private String nombre;
	
	@Lob
	private String descripcion;
	
	@NotNull(message = "No puede ser nulo")
	@Enumerated(value = EnumType.STRING)
	private Unidad unidad;
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 0, message = "Debe ser mayor o igual a cero")
	private Integer stock;
	
	@Min(value = 0, message = "Debe ser mayor o igual a cero")
	@Column(name = "stock_seguridad")
	private Integer stockSeguridad;
	
	@Column(name = "URL_imagen")
	private String URLImagen;
	
	@Min(value = 0, message = "Debe ser mayor o igual a cero")
	@Column(name = "precio_venta")
	private Double precioVenta;
	
	@Min(value = 0, message = "Debe ser mayor o igual a cero")
	@Column(name = "precio_compra")
	private Double precioCompra;
	
	@NotNull(message = "No puede ser nulo")
	@Positive(message = "Debe ser mayor que cero")
	private Double alto;
	
	@NotNull(message = "No puede ser nulo")
	@Positive(message = "Debe ser mayor que cero")
	private Double ancho;
	
	@NotNull(message = "No puede ser nulo")
	@Positive(message = "Debe ser mayor que cero")
	private Double profundo;
	
	@Min(value = 0, message = "Debe ser mayor o igual a cero")
	private Double pesoUnitario;
	
	@ManyToOne
	@ToString.Exclude
	private Estanteria estanteria;
	
}
