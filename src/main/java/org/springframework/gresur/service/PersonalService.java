package org.springframework.gresur.service;

import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.repository.PersonalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalService<T extends Personal, E extends PersonalRepository<T>> {
	
	protected E personalRepo;
	
	@Transactional(readOnly = true)
	public Iterable<T> findAll() throws DataAccessException{
		return personalRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public T findByNIF(String NIF) throws DataAccessException{
		return personalRepo.findByNIF(NIF);
	}
	
	@Transactional
	public T add(T personal) throws DataAccessException{
		return personalRepo.save(personal);
	}
	
	@Transactional
	public void deleteByNIF(String NIF) throws DataAccessException{
		personalRepo.deleteByNIF(NIF);
	}
}
