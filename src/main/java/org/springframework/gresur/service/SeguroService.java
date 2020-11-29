package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public Seguro save(Seguro seguro) throws DataAccessException, FechaFinNotAfterFechaInicioException{
		
		LocalDate fechaInicio = seguro.getFechaContrato();
		LocalDate fechaFin = seguro.getFechaExpiracion();
		
		FechaInicioFinValidation.fechaInicioFinValidation(Seguro.class,fechaInicio, fechaFin);
		
		if(seguro.getFechaExpiracion().isAfter(LocalDate.now())) {
			Vehiculo vehiculo = seguro.getVehiculo();

			if(ITVService.findByVehiculo(vehiculo.getId()).stream().anyMatch(x -> x.getExpiracion().isAfter(LocalDate.now()))) {
				vehiculo.setDisponibilidad(true);
				vehiculoService.save(vehiculo); 
			}
		}
		return seguroRepo.save(seguro);
	}

}
