package org.springframework.gresur.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Producto;

public interface PedidoRepository extends PagingAndSortingRepository<Pedido, Long>{

	List<Pedido> findAll(); 
	
	List<Pedido> findByVehiculoId(Long id); 
	
	List<Pedido> findByEstado(EstadoPedido estado);
	
	Page<Pedido> findByEstado(EstadoPedido estado, Pageable pageable);
		
	List<Pedido> findDistinctByVehiculoIdAndFechaEnvioAndEstadoIn(Long id,LocalDate fecha,Collection<EstadoPedido> lEsta); 
	
	List<Pedido> findByFechaEnvio(LocalDate fecha);
	
	List<Pedido> findByFechaEnvioAndEstadoIn(LocalDate fecha,Collection<EstadoPedido> lEsta);
	
	List<Pedido> findDistinctByVehiculoMatriculaAndEstadoIn(String matricula, Collection<EstadoPedido> lEsta);
	
	@Query(value = "SELECT * FROM PEDIDO P WHERE P.ESTADO IN :estadoPedido INNER JOIN P.FACTURAEMITIDA F INNER JOIN F.LINEASFACTURAS LF WHERE LF.PRODUCTO = :producto", nativeQuery = true)
	List<LineaFactura> findByProductoAndEstadoIn( @Param("estadoPedido") Collection<EstadoPedido> estadoPedido, @Param("producto") Producto producto);
	
	@Query(value = "SELECT * FROM PEDIDOS P WHERE P.ESTADO = :estadoPedido AND P.FECHA_ENVIO < :fecha ORDER BY P.FECHA_REALIZACION DESC", nativeQuery = true)
	List<Pedido> findByEstadoAndFechaEnvioBeforeOrdered(@Param("estadoPedido") String estadoPedido, @Param("fecha") LocalDate fecha);
}
