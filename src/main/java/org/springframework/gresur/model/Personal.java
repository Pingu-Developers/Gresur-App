package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Personal extends Entidad{
	
	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{2}\\s?[0-9]{10}$")
	protected String NSS;
	
	protected String image;
	
	@OneToMany(mappedBy = "emisor", fetch = FetchType.EAGER)
	protected List<Notificacion> noti_enviadas;
	
	@ManyToMany(mappedBy = "receptores")
	protected List<Notificacion> noti_recibidas;
}