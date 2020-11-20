package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Factura;
import org.springframework.gresur.model.LineaFactura;

public interface FacturaRepository<T extends Factura> extends CrudRepository<T, Long> {
	
	public List<LineaFactura> findAllLineasFactura();
}
