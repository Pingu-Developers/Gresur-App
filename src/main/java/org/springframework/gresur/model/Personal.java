package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;

@MappedSuperclass
public class Personal extends Entidad{
	
	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{2}\\s?[0-9]{10}$")
	protected String NSS;
	
	protected String image;
}
