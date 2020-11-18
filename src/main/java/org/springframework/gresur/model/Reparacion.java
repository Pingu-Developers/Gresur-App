package org.springframework.gresur.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
@Entity
@Table(name = "reparaciones")
public class Reparacion extends BaseEntity{

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fechaEntradaTaller;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate fechaSalidaTaller;
	
}
