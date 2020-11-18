package org.springframework.gresur.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Factura;

public interface FacturaRepository<T extends Factura> extends CrudRepository<T, Long> {

}
