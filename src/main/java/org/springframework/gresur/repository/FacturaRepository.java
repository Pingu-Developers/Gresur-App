package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Factura;

public interface FacturaRepository<T extends Factura> extends CrudRepository<T, Long> {	
	List<T> findAll();
	
	List<Double> findImporteByFechaEmisionAfter(LocalDate fecha);

}
