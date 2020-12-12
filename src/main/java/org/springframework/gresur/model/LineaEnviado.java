package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "lineas_enviado", uniqueConstraints = @UniqueConstraint(columnNames = {"notificacion", "personal"}))
public class LineaEnviado extends BaseEntity{


	@NotNull
	public Boolean leido;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "notificacion")
	private Notificacion notificacion;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "personal")
	private Personal personal;
	
	public LineaEnviado(Notificacion notificacion, Personal personal) {
		this.notificacion = notificacion;
		this.personal = personal;
	}
}