package org.springframework.gresur.model;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "encargadosAlmacen")
public class EncargadoDeAlmacen extends Personal{
	
}
