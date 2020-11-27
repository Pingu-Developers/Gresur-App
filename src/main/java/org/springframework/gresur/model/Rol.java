package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="roles")
public class Rol extends BaseEntity{

	@Enumerated(EnumType.STRING)
	private ERol name;
}
