package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Estado;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.PedidoRepository;
import org.springframework.gresur.service.exceptions.MMAExceededException;
import org.springframework.gresur.service.exceptions.VehicleNotAvailableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	private PedidoRepository pedidoRepo;

	@Autowired
	public PedidoService(PedidoRepository pedidoRepo) {
		this.pedidoRepo = pedidoRepo;
	}

	@Transactional(readOnly = true)
	public Iterable<Pedido> findAll() throws DataAccessException {
		return pedidoRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Pedido findByID(Long id) throws DataAccessException {
		return pedidoRepo.findById(id).get();
	}

	@Transactional
	public Pedido add(Pedido pedido) throws DataAccessException, MMAExceededException, VehicleNotAvailableException {
		
		Vehiculo vehiculo = pedido.getVehiculo();
		Double MMA = vehiculo.getMMA();
		LocalDate fecha = pedido.getFechaEnvio();
		
		if(!vehiculo.getDisponibilidad()) {
			throw new VehicleNotAvailableException();
		} else {
			
			List<Pedido> pedidos = vehiculo.getPedidos().stream()
					.filter(x->x.getFechaEnvio().equals(fecha) && x.getEstado().equals(Estado.ABIERTO))
					.collect(Collectors.toList());
			
			//Lista de pedidos, especificando para cada pedido los productos y su cantidad que incluyen
			List<List<LineaFactura>> lineasFactura= pedidos.stream()
					.map(x->x.getFacturaEmitida().getLineasFacturas())
					.collect(Collectors.toList());
					
			Double pesoTotal=0.;
			for (int i = 0; i < lineasFactura.size(); i++) {
				pesoTotal += lineasFactura.get(i).stream().mapToDouble(x->x.getCantidad()*x.getProducto().getPesoUnitario()).sum();
			}
			
			pesoTotal += pedido.getFacturaEmitida().getLineasFacturas().stream()
						.mapToDouble(x->x.getCantidad()*x.getProducto().getPesoUnitario())
						.sum();
			
			if(pesoTotal>MMA) {
				throw new MMAExceededException();
			} else {
				return pedidoRepo.save(pedido);
			}	
		}
	}

	@Transactional
	public void deleteById(Long id) throws DataAccessException {
		pedidoRepo.deleteById(id);
	}

}