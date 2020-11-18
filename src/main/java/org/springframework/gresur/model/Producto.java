package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	private Integer stock;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	@Column(name = "stock_seguridad")
	private Integer stockSeguridad;
	
	@Column(name = "URL_imagen")
	private String URLimagen;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	@Column(name = "precio_venta")
	private Double precioVenta;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	@Column(name = "precio_compra")
	private Double precioCompra;
	
	@Pattern(regexp = "^[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*$")
	private String dimensiones;
	
	@ManyToOne
	private Estanteria estanteria;
	
	//TODO EN LA RELACION PRODUCTO-LINEAFACTURA PASA LO MISMO QUE CON VEHICULO E ITV,SEGURO...
}
