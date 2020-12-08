package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Estado;
import org.springframework.gresur.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

	List<Pedido> findAll(); 
	
	List<Pedido> findByVehiculoId(Long id); 
	
	List<Pedido> findDistinctByVehiculoIdAndFechaEnvioAndEstadoIn(Long id,LocalDate fecha,Collection<Estado> lEsta); 
	
	List<Pedido> findByFechaEnvio(LocalDate fecha);
	
	List<Pedido> findByFechaEnvioAndEstadoIn(LocalDate fecha,Collection<Estado> lEsta);
	
}
