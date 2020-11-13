package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente extends Entidad{
	
	@OneToMany(mappedBy = "cliente", fetch =FetchType.EAGER)
	List<FacturaEmitida> facturasEmitidas;
}
