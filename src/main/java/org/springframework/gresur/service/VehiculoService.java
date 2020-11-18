package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoService {

	private VehiculoRepository vehiculoRepository;

	@Autowired
	public VehiculoService(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Vehiculo> findAll() throws DataAccessException{
		return vehiculoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Vehiculo findById(Long id) throws DataAccessException{
		return vehiculoRepository.findById(id).get();
	}
	
	@Transactional
	public Vehiculo add(Vehiculo vehiculo) throws DataAccessException{
		return vehiculoRepository.save(vehiculo);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		vehiculoRepository.deleteById(id);
	}
	
	
}
