package org.springframework.gresur.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.gresur.model.Transportista;

public interface TransportistaRepository extends PersonalRepository<Transportista>{
	
	@Query(value = "SELECT TRANSPORTISTA_ID FROM "
			+ "(SELECT TRANSPORTISTA_ID,COUNT(P.ID) NUM FROM PEDIDOS P WHERE TRANSPORTISTA_ID IS NOT NULL GROUP BY TRANSPORTISTA_ID ORDER BY NUM ASC) WHERE ROWNUM = 1",
			nativeQuery = true)
	public Optional<Long> findIdTransportistaConMenosPedidos();
}
