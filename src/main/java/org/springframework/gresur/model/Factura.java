package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class Factura{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer numFactura;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fecha;
	
	@NotBlank
	protected Double importe;
	
	@NotBlank
	protected Boolean estaPagada;
		
}
