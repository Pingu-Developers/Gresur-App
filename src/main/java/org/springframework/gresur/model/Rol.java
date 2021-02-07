package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="roles")
public class Rol extends BaseEntity{

	@NotNull(message = "No puede ser nulo")
	@Enumerated(EnumType.STRING)
	private ERol name;
}
