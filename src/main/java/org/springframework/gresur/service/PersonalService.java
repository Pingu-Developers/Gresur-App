package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.repository.PersonalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalService<T extends Personal, E extends PersonalRepository<T>> {
	
	protected E personalGRepo;
	
	@Autowired
	protected PersonalRepository<Personal> personalRepo;
	
	@Transactional(readOnly = true)
	public Iterable<T> findAll() throws DataAccessException{
		return personalGRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Personal> findAllPersonal() throws DataAccessException{
		return personalRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Personal findPersonal(Long id) throws DataAccessException{
		return personalRepo.findById(id).orElse(null);
	}
	
	
	@Transactional(readOnly = true)
	public T findByNIF(String NIF) throws DataAccessException{
		return personalGRepo.findByNIF(NIF);
	}
	
	@Transactional
	public T add(T personal) throws DataAccessException{
		return personalGRepo.save(personal);
	}
	
	@Transactional
	public void deleteByNIF(String NIF) throws DataAccessException{
		personalGRepo.deleteByNIF(NIF);
	}
}
