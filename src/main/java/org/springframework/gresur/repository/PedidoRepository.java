package org.springframework.gresur.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

	List<Pedido> findAll(); 
	
}
