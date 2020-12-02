package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.SeguroRepository;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.gresur.util.FechaInicioFinValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguroService {
	
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
	public Iterable<Seguro> findAll() throws DataAccessException{
		return seguroRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Seguro findById(Long id) throws DataAccessException{
		return seguroRepo.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Seguro> findByVehiculo(Long id) throws DataAccessException{
		return seguroRepo.findByVehiculo(id);
	}
	@Transactional
	public void deleteAll() {
		seguroRepo.deleteAll();
	}
	
	//TODO Revisar Max en JSQL
	@Transactional(readOnly = true)
	public Seguro findLastSeguroByVehiculo(Long id) {
		return seguroRepo.findByVehiculoIdAndFechaExpiracionAfter(id, LocalDate.now()).stream()
				.max((x,y) -> x.getFechaExpiracion().compareTo(y.getFechaExpiracion())).orElse(null);
	}
	
	@Transactional
	public Seguro save(Seguro seguro) throws DataAccessException, FechaFinNotAfterFechaInicioException{
		
		LocalDate fechaInicio = seguro.getFechaContrato();
		LocalDate fechaFin = seguro.getFechaExpiracion();
		
		FechaInicioFinValidation.fechaInicioFinValidation(Seguro.class,fechaInicio, fechaFin);
		
		if(seguro.getFechaExpiracion().isAfter(LocalDate.now())) {
			Vehiculo vehiculo = seguro.getVehiculo();

			if(ITVService.findLastITVFavorableByVehiculo(vehiculo.getId()) != null) {
				vehiculo.setDisponibilidad(true);
				vehiculoService.save(vehiculo); 
			}
		}
		return seguroRepo.save(seguro);
	}

	public Long count() {
		return seguroRepo.count();
	}

}
