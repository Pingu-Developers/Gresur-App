package org.springframework.gresur.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.SeguroRepository;
import org.springframework.gresur.repository.VehiculoRepository;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguroService {
	
	private SeguroRepository seguroRepo;
	private VehiculoRepository vehiculoRepo;
	
	@Autowired
	public SeguroService(SeguroRepository seguroRepo, VehiculoRepository vehiculoRepo) {
		this.seguroRepo = seguroRepo;
		this.vehiculoRepo = vehiculoRepo;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Seguro> findAll() throws DataAccessException{
		return seguroRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Seguro findById(Long id) throws DataAccessException{
		return seguroRepo.findById(id).orElse(null);
	}
	
	@Transactional
	public Seguro save(Seguro seguro) throws DataAccessException, FechaFinNotAfterFechaInicioException{
		
		LocalDate fechaInicio = seguro.getFechaContrato();
		LocalDate fechaFin = seguro.getFechaExpiracion();
		
		if(fechaInicio.isAfter(fechaFin)) {
			throw new FechaFinNotAfterFechaInicioException("La fecha de inicio no puede ser una fecha posterior a la de finalizacion!");
		}
		
		if(seguro.getFechaExpiracion().isAfter(LocalDate.now())) {
			Vehiculo vehiculo = seguro.getVehiculo();
			if(vehiculo.getITVs().stream().anyMatch(x -> x.getExpiracion().isAfter(LocalDate.now()))) {
				vehiculo.setDisponibilidad(true);
				vehiculoRepo.save(vehiculo);
			}
		}
		
		return seguroRepo.save(seguro);
	}

}
