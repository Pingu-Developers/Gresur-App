package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Entity
@Data
@Table(name = "productos")
public class Producto extends BaseEntity{
	
	@NotBlank
	protected String nombre;
	
	protected String descripcion;
	
	@NotBlank
	protected String unidad;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	protected Integer stock;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	protected Integer stockSeguridad;
	
	protected String URLimagen;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	protected Double precioVenta;
	
	@Min(value = 0, message = "debe ser mayor o igual a cero")
	protected Double precioCompra;
	
	@Pattern(regexp = "^[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*x{1}[0-9]+[,.]?[0-9]*$")
	protected String dimensiones;
	
	@Min(value = 0, message = "debe estar entre 0 y 1")
	@Max(value = 1, message = "debe estar entre 0 y 1")
	protected Double demanda;
	

}
