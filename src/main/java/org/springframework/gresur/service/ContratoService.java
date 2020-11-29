package org.springframework.gresur.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.repository.ContratoRepository;
import org.springframework.gresur.service.exceptions.SalarioMinimoException;
import org.springframework.gresur.util.FechaInicioFinValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoService {
	
	private ContratoRepository contratoRepository;
	
	@Autowired
	private ConfiguracionService configService;
	
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
	
	//TODO REVISAR LOS THROWS YA QUE ALGUNOS LO TIENEN Y OTROS NO (EXCEPCION ES RUNTIMEEXCEPTION HARIA FALTA ENTONCES?)
	@Transactional
	public Contrato save(Contrato contrato) throws DataAccessException{
		
		LocalDate fechaInicio = contrato.getFechaInicio();
		LocalDate fechaFin = contrato.getFechaFin();
		
		if(contrato.getNomina() < configService.getSalarioMinimo()) {
			throw new SalarioMinimoException();
		} 

		FechaInicioFinValidation.fechaInicioFinValidation(Contrato.class,fechaInicio, fechaFin);
		
		return contratoRepository.save(contrato);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		contratoRepository.deleteById(id);
	}
}
