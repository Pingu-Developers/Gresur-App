package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "dependientes")
public class Dependiente extends Personal{

	@OneToMany(mappedBy = "dependiente")
	private List<FacturaEmitida> facturasEmitidas;
	
}
