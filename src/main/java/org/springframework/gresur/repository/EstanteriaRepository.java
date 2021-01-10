package org.springframework.gresur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Estanteria;

public interface EstanteriaRepository extends CrudRepository<Estanteria, Long>{
	
	List<Estanteria> findAll();
	List<Estanteria> findByAlmacenId(Long id);

	@Query(value = "SELECT SUM(CAPACIDAD) FROM ESTANTERIAS WHERE ALMACEN_ID = :almacen AND ID <> :estanteriaId", nativeQuery = true)
	Optional<Double> sumCapacidadEstanteriasAlmacenNotEqualTo(@Param("almacen") Almacen almacen, @Param("estanteriaId") Long estanteriaId);
}
