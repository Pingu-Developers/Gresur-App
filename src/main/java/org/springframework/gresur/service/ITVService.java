package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.ITVRepository;
import org.springframework.gresur.util.FechaInicioFinValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ITVService {

	private ITVRepository itvRepository;

	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private SeguroService seguroService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	public ITVService(ITVRepository itvRepository) {
		this.itvRepository = itvRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<ITV> findAll() throws DataAccessException{
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
		return (itv != null && itv.getResultado().equals(ResultadoITV.FAVORABLE) && itv.getExpiracion().isAfter(LocalDate.now())) ? itv : null;
	}
			
	@Transactional
	public ITV save(ITV itv) throws DataAccessException{
		
		LocalDate fechaInicio = itv.getFecha();
		LocalDate fechaFin = itv.getExpiracion();		
		
		FechaInicioFinValidation.fechaInicioFinValidation(ITV.class,fechaInicio, fechaFin);
		
		Vehiculo vehiculo = itv.getVehiculo();
		ITV ultimaITVGuardada = findLastITVVehiculo(vehiculo.getMatricula());
		Boolean isLast = ultimaITVGuardada == null || !ultimaITVGuardada.getExpiracion().isAfter(itv.getExpiracion());
		
		if(isLast && itv.getResultado().equals(ResultadoITV.FAVORABLE) && seguroService.findLastSeguroByVehiculo(vehiculo.getMatricula()) != null) {
			vehiculo.setDisponibilidad(true);	
		}	

		ITV ret = itvRepository.save(itv);
		vehiculoService.save(vehiculo);
		em.flush();
		return ret;
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		itvRepository.deleteById(id);
	}
}
