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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "facturas")
public class Factura{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ENTITY_ID")
	protected Long id;
	
	@NotNull
	protected LocalDate fecha;
	
	@NotNull
	@Min(value=0, message = "debe ser mayor o igual a cero")  
	protected Double importe;
	
	@NotNull
	@Column(name = "esta_pagada")
	protected Boolean estaPagada;
	
	@JsonIgnore
	@OneToMany(mappedBy = "factura", cascade = CascadeType.REMOVE)
	protected List<LineaFactura> lineasFacturas;
}
