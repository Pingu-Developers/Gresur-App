package org.springframework.gresur.model;

import java.time.LocalDateTime;
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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="notificaciones")
public class Notificacion extends BaseEntity{

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name ="tipo_notificacion")
	private TipoNotificacion tipoNotificacion;
	
	@NotBlank
	@Lob
	private String cuerpo;
	
	@NotNull
	private LocalDateTime fechaHora;
	
	@NotNull
	private Boolean leido;
	
	@JsonIgnore
	@ManyToOne
	private Personal emisor;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name ="enviado_a",
			joinColumns = @JoinColumn(name = "notificacion_id"),
			inverseJoinColumns = @JoinColumn(name = "personal_id"))
	@Size(min = 1)
	private List<Personal> receptores;
	
}
