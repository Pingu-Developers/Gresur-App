package org.springframework.gresur.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity 
@Table(name="vehiculos")
public class ITV extends BaseEntity{
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	protected LocalDate fecha;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	protected LocalDate expiracion;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	protected ResultadoITV resultado;
}
