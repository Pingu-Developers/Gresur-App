package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.LineaFactura;

public interface LineasFacturaRepository extends CrudRepository<LineaFactura, Long> {
	
	public List<LineaFactura> findAll();


}
