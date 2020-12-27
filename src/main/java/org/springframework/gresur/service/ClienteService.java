package org.springframework.gresur.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
	
	@PersistenceContext
	private EntityManager em;
	
	private ClienteRepository clienteRepo;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepo) {
		this.clienteRepo = clienteRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Cliente> findAll() throws DataAccessException{
		return clienteRepo.findAll();
	}
	
	@Transactional
	public void deleteAll() {
		this.clienteRepo.deleteAll();
	}
	
	@Transactional(readOnly = true)
	public Cliente findByNIF(String NIF) throws DataAccessException{
		return clienteRepo.findByNIF(NIF);
	}
	
	@Transactional
	public Cliente save(Cliente cliente) throws DataAccessException {
		em.clear();

		Cliente ret = clienteRepo.save(cliente);
		em.flush();
		return ret;
	}
	
	@Transactional
	public void deleteByNIF(String NIF) throws DataAccessException{
		clienteRepo.deleteByNIF(NIF);
	}


	@Transactional
	public Long count() {
		return clienteRepo.count();
	} 
}
