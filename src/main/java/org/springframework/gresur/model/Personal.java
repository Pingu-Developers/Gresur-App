package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Personal extends Entidad{
	
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[0-9]{2}\\s?[0-9]{10}$")
	protected String NSS;
	
	protected String image;
	
}
