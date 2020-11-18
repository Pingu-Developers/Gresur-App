package org.springframework.gresur.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long>{

}
