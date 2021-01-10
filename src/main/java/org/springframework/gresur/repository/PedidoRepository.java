package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Producto;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

	List<Pedido> findAll(); 
	
	List<Pedido> findByVehiculoId(Long id); 
	
	List<Pedido> findByEstado(EstadoPedido estado); 
	
	List<Pedido> findDistinctByVehiculoIdAndFechaEnvioAndEstadoIn(Long id,LocalDate fecha,Collection<EstadoPedido> lEsta); 
	
	List<Pedido> findByFechaEnvio(LocalDate fecha);
	
	List<Pedido> findByFechaEnvioAndEstadoIn(LocalDate fecha,Collection<EstadoPedido> lEsta);
	
	List<Pedido> findDistinctByVehiculoMatriculaAndEstadoIn(String matricula, Collection<EstadoPedido> lEsta);
	
	@Query(value = "SELECT * FROM PEDIDO P WHERE P.ESTADO IN :estadoPedido INNER JOIN P.FACTURAEMITIDA F INNER JOIN F.LINEASFACTURAS LF WHERE LF.PRODUCTO = :producto", nativeQuery = true)
	List<LineaFactura> findByProductoAndEstadoIn( @Param("estadoPedido") Collection<EstadoPedido> estadoPedido, @Param("producto") Producto producto);
}
