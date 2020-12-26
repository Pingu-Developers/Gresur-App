package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.Vehiculo;
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
	public List<Reparacion> findAll() throws DataAccessException{
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
	
	@Transactional(readOnly = true)
	public Reparacion findLastReparacionByVehiculo(String matricula) {
		List<Reparacion> l = this.findByMatricula(matricula);
		l.sort(Comparator.reverseOrder());
		return l.size() == 0 ? null : l.get(0);
	}
	
	@Transactional
	public Reparacion save(Reparacion reparacion) throws DataAccessException {
		em.clear();
		
		LocalDate fechaInicio = reparacion.getFechaEntradaTaller();
		LocalDate fechaFin = reparacion.getFechaSalidaTaller();
		
		FechaInicioFinValidation.fechaInicioFinValidation(Reparacion.class,fechaInicio, fechaFin);
		
		Vehiculo v = reparacion.getVehiculo();
		Reparacion ultimaGuardada = this.findLastReparacionByVehiculo(v.getMatricula());
		Boolean isLast = ultimaGuardada == null || (fechaFin == null ? !ultimaGuardada.getFechaEntradaTaller().isAfter(fechaInicio) : !ultimaGuardada.getFechaSalidaTaller().isAfter(fechaFin));
		
		if(!isLast && fechaFin == null) {
			throw new IllegalArgumentException("Solo la ultima reparacion de un vehiculo puede tener fecha de salida desconocida");
		}
		
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
	
	@Transactional
	public void deleteByRecibidasId(Long id) throws DataAccessException{
		reparacionRepo.deleteByRecibidasId(id);
	}
	
	@Transactional
	public Long count() {
		return reparacionRepo.count();
	}
	
}
