package org.springframework.gresur.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.repository.SeguroRepository;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguroService {
	
	private SeguroRepository seguroRepo;
	
	@Autowired
	public SeguroService(SeguroRepository seguroRepo) {
		this.seguroRepo = seguroRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Seguro> findAll() throws DataAccessException{
		return seguroRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Seguro findById(Long id) throws DataAccessException{
		return seguroRepo.findById(id).orElse(null);
	}
	
	@Transactional
	public Seguro save(Seguro seguro) throws DataAccessException, FechaFinNotAfterFechaInicioException{
		
		LocalDate fechaInicio = seguro.getFechaContrato();
		LocalDate fechaFin = seguro.getFechaExpiracion();
		
		if(fechaInicio.isAfter(fechaFin)) {
			throw new FechaFinNotAfterFechaInicioException("La fecha de inicio no puede ser una fecha posterior a la de finalizacion!");
		}
		
		return seguroRepo.save(seguro);
	}

}
