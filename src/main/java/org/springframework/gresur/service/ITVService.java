package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.repository.ITVRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ITVService {

	private ITVRepository itvRepository;

	@Autowired
	public ITVService(ITVRepository itvRepository) {
		this.itvRepository = itvRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<ITV> findAll() throws DataAccessException{
		return itvRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public ITV findById(Long id) throws DataAccessException{
		return itvRepository.findById(id).get();
	}
	
	@Transactional
	public ITV save(ITV itv) throws DataAccessException{
		return itvRepository.save(itv);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		itvRepository.deleteById(id);
	}
}
