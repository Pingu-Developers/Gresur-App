package org.springframework.gresur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Producto;

public interface ProductoRepository extends PagingAndSortingRepository<Producto, Long>{
	
	List<Producto> findAll();
	
	List<Producto> findByNombreContainingIgnoreCase(String s);
	
	List<Producto> findByEstanteriaCategoria(Categoria c);
	
	Page<Producto> findByEstanteriaCategoria(Categoria categoria, Pageable pageable);
	
	Page<Producto> findByNombreContainingIgnoreCase(String s, Pageable pageable);
		
	@Query(value = "SELECT SUM(P.ANCHO * P.ALTO * P.PROFUNDO * P.STOCK) FROM PRODUCTOS P WHERE P.ESTANTERIA_ID = :estanteria AND P.NOMBRE <> :nombre", nativeQuery = true)
	Optional<Double> sumStockProductosEstanteriaNotNombre(@Param("estanteria") Long estanteria, @Param("nombre") String nombre);

}
