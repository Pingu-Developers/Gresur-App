package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.gresur.model.FacturaEmitida;

public interface FacturaEmitidaRepository extends FacturaRepository<FacturaEmitida>{
	
	List<FacturaEmitida> findByClienteIdAndEstaPagadaFalse(Long id);
	
	List<FacturaEmitida> findByClienteIdAndEstaPagadaTrue(Long id);
	
	List<FacturaEmitida> findByClienteId(Long id);
	
	List<FacturaEmitida> findByClienteIdAndFechaEmision(Long id,LocalDate fecha);
	
	List<FacturaEmitida> findByFechaEmisionBeforeAndFechaEmisionAfter(LocalDate dateBefore, LocalDate dateAfter);
	
	FacturaEmitida findByNumFactura(String numFactura);
	
	List<FacturaEmitida> findAll(Pageable pageable);
}
