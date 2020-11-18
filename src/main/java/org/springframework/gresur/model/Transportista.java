package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transportistas")
public class Transportista extends Personal{
	
	@OneToMany(mappedBy = "transportista")
	@Size(min = 1)
	private List<Vehiculo> vehiculos;
}
