package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Seguro;

public interface SeguroRepository extends CrudRepository<Seguro, Long> {	
	
	List<Seguro> findByVehiculoMatricula(String Matricula);
	
	List<Seguro> findByVehiculoMatriculaAndFechaExpiracionAfter(String matricula, LocalDate fecha);
	
}
