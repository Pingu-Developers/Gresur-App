package org.springframework.gresur.model;

import java.sql.Blob;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="notificaciones")
public class Notificacion extends BaseEntity{

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name ="tipo_notificacion")
	private TipoNotificacion tipoNotificacion;
	
	//@NotBlank
	@Lob
	private Blob cuerpo;
	
	@NotNull
	private Boolean leido;
	
	@ManyToOne(optional = true)
	private Personal emisor;
	
	@ManyToMany
	@JoinTable(
			name ="enviado_a",
			joinColumns = @JoinColumn(name = "notificacion_id"),
			inverseJoinColumns = @JoinColumn(name = "personal_id"))
	private List<Personal> receptores;
	
}
