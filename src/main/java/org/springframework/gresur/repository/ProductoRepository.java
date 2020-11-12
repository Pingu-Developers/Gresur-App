package org.springframework.gresur.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer>{

	Producto findByid(Integer id);

	void deleteByid(Producto id);

}
