package org.springframework.gresur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.LineaFactura;

public interface LineasFacturaRepository extends CrudRepository<LineaFactura, Long> {
	
	public List<LineaFactura> findAll();
	
	public Optional<LineaFactura> findByFacturaIdAndProductoId(Long idFac,Long idProd);

}
