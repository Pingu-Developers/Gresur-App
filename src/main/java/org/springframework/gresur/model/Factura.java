package org.springframework.gresur.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "facturas")
public class Factura{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ENTITY_ID")
	protected Long id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fecha;
	
	@NotNull
	protected Double importe;
	
	@NotNull
	@Column(name = "esta_pagada")
	protected Boolean estaPagada;
	
	@OneToMany(mappedBy = "factura", cascade = CascadeType.REMOVE)
	protected List<LineaFactura> lineasFacturas;
}
