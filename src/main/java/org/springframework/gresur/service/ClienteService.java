package org.springframework.gresur.service;

import java.util.function.IntPredicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	private ClienteRepository clienteRepo;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepo) {
		this.clienteRepo = clienteRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Cliente> findAll() throws DataAccessException{
		return clienteRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Cliente findByNIF(String NIF) throws DataAccessException{
		return clienteRepo.findByNIF(NIF);
	}
	
	@Transactional
	public Cliente save(Cliente cliente) throws DataAccessException {
		return clienteRepo.save(cliente);
	}
	
	@Transactional
	public void deleteByNIF(String NIF) throws DataAccessException{
		clienteRepo.deleteByNIF(NIF);
	}

	@Transactional
	public void deletAll() {
		clienteRepo.deleteAll();
		
	}

	@Transactional
	public Long count() {
		return clienteRepo.count();
	} 
}
