package org.springframework.gresur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.LineaFactura;

public interface LineasFacturaRepository extends CrudRepository<LineaFactura, Long> {

	List<LineaFactura> findAll();
	
	Optional<LineaFactura> findByFacturaIdAndProductoId(Long idFac,Long idProd);
	
	void deleteByFacturaId(Long id);

}
