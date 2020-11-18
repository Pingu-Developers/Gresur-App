package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.repository.PedidoRepository;
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
	public Iterable<Pedido> findAll() throws DataAccessException{
		return pedidoRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Pedido findByID(Long id) throws DataAccessException{
		return pedidoRepo.findById(id).get();
	}
	
	@Transactional
	public Pedido add(Pedido pedido) throws DataAccessException {
		return pedidoRepo.save(pedido);
	}
	
	@Transactional
	public void deleteByID(Long id) throws DataAccessException{
		pedidoRepo.deleteById(id);
	} 

	

}
