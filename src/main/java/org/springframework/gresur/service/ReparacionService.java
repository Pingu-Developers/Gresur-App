package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.repository.ReparacionRepository;
import org.springframework.gresur.util.FechaInicioFinValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReparacionService {

	@PersistenceContext
	private EntityManager em;
	
	private ReparacionRepository reparacionRepo;
	
	@Autowired
	public ReparacionService(ReparacionRepository reparacionRepo) {
		this.reparacionRepo = reparacionRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Reparacion> findAll() throws DataAccessException{
		return reparacionRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Reparacion findById(Long id) throws DataAccessException{
		return reparacionRepo.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public List<Reparacion> findByMatricula(String matricula) throws DataAccessException{
		return reparacionRepo.findByVehiculoMatricula(matricula);
	}
	
	@Transactional
	public Reparacion save(Reparacion reparacion) throws DataAccessException {
		
		LocalDate fechaInicio = reparacion.getFechaEntradaTaller();
		LocalDate fechaFin = reparacion.getFechaSalidaTaller();
		
		FechaInicioFinValidation.fechaInicioFinValidation(Reparacion.class,fechaInicio, fechaFin);
		
		Reparacion ret = reparacionRepo.save(reparacion);
		em.flush();
		return ret;
	}

	@Transactional
	public void deleteAll() throws DataAccessException{
		reparacionRepo.deleteAll();
	}
	
	@Transactional
	public void deleteAll(List<Reparacion> lr) throws DataAccessException{
		reparacionRepo.deleteAll(lr);
	}
	
	@Transactional
	public void deleteByVehiculoId(Long id) throws DataAccessException{
		reparacionRepo.deleteByVehiculoId(id);
	}
	
	@Transactional
	public void deleteByVehiculoMatricula(String matricula) throws DataAccessException{
		reparacionRepo.deleteByVehiculoMatricula(matricula);
	}
	
}
