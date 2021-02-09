package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.gresur.model.FacturaRecibida;

public interface FacturaRecibidaRepository extends FacturaRepository<FacturaRecibida> {
	
	List<FacturaRecibida> findByFechaEmisionBeforeAndFechaEmisionAfter(LocalDate dateBefore, LocalDate dateAfter);
	
}
