package org.springframework.gresur.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.repository.ContratoRepository;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoService {
	
	private ContratoRepository contratoRepository;
	
	@Autowired
	public ContratoService(ContratoRepository contratoRepository) {
		this.contratoRepository = contratoRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Contrato> findAll() throws DataAccessException{
		return contratoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Contrato findById(Long id) throws DataAccessException{
		return contratoRepository.findById(id).get();
	}
	
	@Transactional
	public Contrato save(Contrato contrato) throws DataAccessException{
		
		LocalDate fechaInicio = contrato.getFechaInicio();
		LocalDate fechaFin = contrato.getFechaFin();
		
		if(fechaInicio.isAfter(fechaFin)) {
			throw new FechaFinNotAfterFechaInicioException("La fecha de inicio no puede ser una fecha posterior a la de finalizacion!");
		}
		
		return contratoRepository.save(contrato);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		contratoRepository.deleteById(id);
	}
}
