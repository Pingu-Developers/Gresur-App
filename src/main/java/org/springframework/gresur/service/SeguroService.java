package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.SeguroRepository;
import org.springframework.gresur.util.FechaInicioFinValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguroService {

	@PersistenceContext
	private EntityManager em;
	
	private SeguroRepository seguroRepo;

	@Autowired
	private ITVService ITVService;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	public SeguroService(SeguroRepository seguroRepo) {
		this.seguroRepo = seguroRepo;
	}
	
	@Transactional(readOnly = true)
	public List<Seguro> findAll() throws DataAccessException{
		return seguroRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Seguro findById(Long id) throws DataAccessException{
		return seguroRepo.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Seguro> findByVehiculo(String matricula) throws DataAccessException{
		return seguroRepo.findByVehiculoMatricula(matricula);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		seguroRepo.deleteById(id);
	}
	
	@Transactional
	public void deleteByVehiculoId(Long id) throws DataAccessException{
		seguroRepo.deleteByVehiculoId(id);
	}
	
	@Transactional
	public void deleteByRecibidasId(Long id) throws DataAccessException{
		seguroRepo.deleteByRecibidasId(id);
	}
	
	@Transactional
	public void deleteByVehiculoMatricula(String matricula) throws DataAccessException{
		seguroRepo.deleteByVehiculoMatricula(matricula);;
	}
	
	@Transactional
	public void deleteAll() {
		seguroRepo.deleteAll();
	}
	@Transactional
	public void deleteAll(List<Seguro> ls) {
		seguroRepo.deleteAll(ls);
	}
	
	@Transactional(readOnly = true)
	public Seguro findLastSeguroByVehiculo(String matricula) {
		return seguroRepo.findFirstByVehiculoMatriculaOrderByFechaExpiracionDesc(matricula);
	}
	
	@Transactional
	public Seguro save(Seguro seguro) throws DataAccessException{
		
		LocalDate fechaInicio = seguro.getFechaContrato();
		LocalDate fechaFin = seguro.getFechaExpiracion();
		
		FechaInicioFinValidation.fechaInicioFinValidation(Seguro.class,fechaInicio, fechaFin);
		
		Vehiculo vehiculo = seguro.getVehiculo();
		Seguro ultimoSeguroGuardado = findLastSeguroByVehiculo(vehiculo.getMatricula());
		Boolean isLast = ultimoSeguroGuardado == null || !ultimoSeguroGuardado.getFechaExpiracion().isAfter(seguro.getFechaExpiracion());
			

		if(isLast && ITVService.findLastITVFavorableByVehiculo(vehiculo.getMatricula()) != null) {
			vehiculo.setDisponibilidad(true);			
		}
		
		Seguro ret = seguroRepo.save(seguro);
		vehiculoService.save(vehiculo); 
		em.flush();
		return ret;
	}
	
	@Transactional
	public Long count() {
		return seguroRepo.count();
	}

}
