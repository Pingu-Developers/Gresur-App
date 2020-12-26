package org.springframework.gresur.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	@OneToMany(mappedBy = "factura", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	protected List<LineaFactura> lineasFacturas;
	
	@Lob
	private String descripcion;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estaPagada == null) {
			if (other.estaPagada != null)
				return false;
		} else if (!estaPagada.equals(other.estaPagada))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (lineasFacturas == null) {
			if (other.lineasFacturas != null)
				return false;
		} else if (other.lineasFacturas != null && !(lineasFacturas.containsAll(other.lineasFacturas) && other.lineasFacturas.containsAll(lineasFacturas)))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((estaPagada == null) ? 0 : estaPagada.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result + ((lineasFacturas == null) ? 0 : lineasFacturas.hashCode());
		return result;
	}
	
	
}
