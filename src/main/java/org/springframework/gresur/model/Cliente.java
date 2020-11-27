package org.springframework.gresur.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clientes")
public class Cliente extends Entidad{
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<FacturaEmitida> facturasEmitidas;
}
