package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.repository.EstanteriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstanteriaService{
	
	private EstanteriaRepository estanteriaRepository;
	
	@Autowired
	public EstanteriaService(EstanteriaRepository estanteriaRepository) {
		this.estanteriaRepository = estanteriaRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Estanteria> findAll() throws DataAccessException{
		return estanteriaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Estanteria findById(Long id) throws DataAccessException{
		return estanteriaRepository.findById(id).get();
	}
	
	@Transactional
	public Estanteria add(Estanteria estanteria) throws DataAccessException {
		return estanteriaRepository.save(estanteria);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		estanteriaRepository.deleteById(id);
	} 
	

}
