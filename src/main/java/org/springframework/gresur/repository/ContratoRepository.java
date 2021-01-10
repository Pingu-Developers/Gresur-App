package org.springframework.gresur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Contrato;

public interface ContratoRepository extends CrudRepository<Contrato, Long>{
	List<Contrato> findAll();
	
	void deleteByPersonalNIF(String NIF);
	
	Optional<Contrato>  findByPersonalNIF(String NIF);
}
