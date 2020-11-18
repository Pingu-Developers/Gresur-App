package org.springframework.gresur.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "seguros")
public class Seguro extends BaseEntity{
	
	@NotBlank
	protected String compañia;
	
	protected TipoSeguro tipoSeguro;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fechaContrato;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fechaExpiracion;
	
}
