package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	@PersistenceContext
	private EntityManager em;
	
	private ContratoRepository contratoRepository;
	
	@Autowired
	private ConfiguracionService configService;
	
	@Autowired
	public ContratoService(ContratoRepository contratoRepository) {
		this.contratoRepository = contratoRepository;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	
	@Transactional(readOnly = true)
	public List<Contrato> findAll() throws DataAccessException{
		return contratoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Contrato findById(Long id) throws DataAccessException{
		return contratoRepository.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public Contrato findByPersonalNIF(String nif) throws DataAccessException{
		return contratoRepository.findByPersonalNIF(nif).orElse(null);
	}
	
	@Transactional
	public Contrato save(Contrato contrato) throws DataAccessException{
		em.clear();
		
		LocalDate fechaInicio = contrato.getFechaInicio();
		LocalDate fechaFin = contrato.getFechaFin();
		
		if(contrato.getNomina() < configService.getSalarioMinimo()) {
			throw new SalarioMinimoException("El salario es menor que el salario minimo");
		} 

		FechaInicioFinValidation.fechaInicioFinValidation(Contrato.class,fechaInicio, fechaFin);
		
		Contrato ret = contratoRepository.save(contrato);
		em.flush();
		return ret;
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		contratoRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteAll() {
		contratoRepository.deleteAll();
	}
	
	@Transactional
	public void deleteByPersonalNIF(String NIF) {
		contratoRepository.deleteByPersonalNIF(NIF);
	}

	@Transactional
	public Long count() {
		return contratoRepository.count();
	}
}
