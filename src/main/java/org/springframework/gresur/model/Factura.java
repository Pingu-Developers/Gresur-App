package org.springframework.gresur.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class Factura{
	
	@Id
	@Column(name = "num_factura")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer numFactura;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fecha;
	
	@NotBlank
	protected Double importe;
	
	@NotBlank
	@Column(name = "esta_pagada")
	protected Boolean estaPagada;
	
//	@OneToMany(mappedBy = "factura", fetch = FetchType.EAGER)
//	List<LineaFactura> lineasFacturas;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	Producto producto; FALTA EN PRODUCTO AÑADIR EL @ManyToMany con el fetch y el mapped!!!
}
