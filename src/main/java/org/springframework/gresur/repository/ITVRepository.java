package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.ResultadoITV;

public interface ITVRepository extends CrudRepository<ITV, Long>{
	
	List<ITV> findByVehiculoId(Long id);
	
	List<ITV> findByVehiculoIdAndExpiracionAfterAndResultadoIn(Long id, LocalDate fecha,Collection<ResultadoITV> res);
	
}
