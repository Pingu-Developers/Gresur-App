package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public class NamedEntity extends BaseEntity {
	
	@NotBlank(message = "No puede ser vacio")
    @Size(min = 3, max = 50, message = "Debe ser de entre 3 y 50 caracteres")
	@Column(name = "name")
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
