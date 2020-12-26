package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.repository.ITVRepository;
import org.springframework.gresur.util.FechaInicioFinValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ITVService {

	@PersistenceContext
	private EntityManager em;
	
	private ITVRepository itvRepository;

	@Autowired
	public ITVService(ITVRepository itvRepository) {
		this.itvRepository = itvRepository;
	}
	
	@Transactional(readOnly = true)
	public List<ITV> findAll() throws DataAccessException{
		return itvRepository.findAll();
	}
	
	@Transactional
	public void deleteAll() {
		this.itvRepository.deleteAll();
	}
	@Transactional
	public void deleteAll(List<ITV> li) {
		this.itvRepository.deleteAll(li);
	}
	
	@Transactional(readOnly = true)
	public ITV findById(Long id) throws DataAccessException{
		return itvRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public List<ITV> findByVehiculo(String matricula) throws DataAccessException{
		return itvRepository.findByVehiculoMatricula(matricula);
	}
	
	@Transactional(readOnly = true)
	public ITV findLastITVVehiculo(String matricula) {
		return itvRepository.findFirstByVehiculoMatriculaOrderByExpiracionDesc(matricula).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public ITV findLastITVFavorableByVehiculo(String matricula) {
		ITV itv = findLastITVVehiculo(matricula);
		return (itv != null && itv.getResultado().equals(ResultadoITV.FAVORABLE) && !itv.getExpiracion().isBefore(LocalDate.now())) ? itv : null;
	}
			
	@Transactional
	public ITV save(ITV itv) throws DataAccessException{
		em.clear();
		
		LocalDate fechaInicio = itv.getFecha();
		LocalDate fechaFin = itv.getExpiracion();		
		
		FechaInicioFinValidation.fechaInicioFinValidation(ITV.class,fechaInicio, fechaFin);
		
		ITV ret = itvRepository.save(itv);
		em.flush();
		return ret;
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		itvRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteByVehiculoId(Long id) throws DataAccessException{
		itvRepository.deleteByVehiculoId(id);
	}
	
	@Transactional
	public void deleteByVehiculoMatricula(String matricula) throws DataAccessException{
		itvRepository.deleteByVehiculoMatricula(matricula);
	}
	
	@Transactional
	public void deleteByRecibidasId(Long id) throws DataAccessException{
		itvRepository.deleteByRecibidasId(id);
	}
	
	@Transactional
	public Long count() {
		return itvRepository.count();
	}
}
