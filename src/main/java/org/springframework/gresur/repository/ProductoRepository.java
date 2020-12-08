package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long>{
	
	List<Producto> findAll();
	
	List<Producto> findByNombreContainingIgnoreCase(String s);
	
	List<Producto> findByEstanteriaCategoria(Categoria c);

}
