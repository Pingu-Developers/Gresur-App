package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dependientes")
public class Dependiente extends Personal{

	@OneToMany(mappedBy = "dependiente", fetch = FetchType.EAGER)
	List<FacturaEmitida> facturasEmitidas;
	
}
