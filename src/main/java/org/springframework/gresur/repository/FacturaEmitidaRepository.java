package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.gresur.model.FacturaEmitida;

public interface FacturaEmitidaRepository extends FacturaRepository<FacturaEmitida>{
	
	List<FacturaEmitida> findByClienteIdAndEstaPagadaFalse(Long id);
	
}
