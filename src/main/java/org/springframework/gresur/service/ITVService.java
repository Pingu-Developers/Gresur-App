package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.ITVRepository;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ITVService {

	private ITVRepository itvRepository;

	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private SeguroService seguroService;
	
	@Autowired
	public ITVService(ITVRepository itvRepository) {
		this.itvRepository = itvRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<ITV> findAll() throws DataAccessException{
		return itvRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public ITV findById(Long id) throws DataAccessException{
		return itvRepository.findById(id).get();
	}
	@Transactional(readOnly = true)
	public List<ITV> findByVehiculo(Long id) throws DataAccessException{
		return itvRepository.findByVehiculo(id);
	}
	
	
	@Transactional
	public ITV save(ITV itv) throws DataAccessException, FechaFinNotAfterFechaInicioException{
		
		LocalDate fechaInicio = itv.getFecha();
		LocalDate fechaFin = itv.getExpiracion();
		
		if(fechaInicio.isAfter(fechaFin)) {
			throw new FechaFinNotAfterFechaInicioException("La fecha de inicio no puede ser una fecha posterior a la de finalizacion!");
		}
		if(itv.getExpiracion().isAfter(LocalDate.now())) {
			Vehiculo vehiculo = itv.getVehiculo();
			if(seguroService.findByVehiculo(vehiculo.getId()).stream().anyMatch(x -> x.getFechaExpiracion().isAfter(LocalDate.now()))) {
				vehiculo.setDisponibilidad(true);
				vehiculoService.save(vehiculo);
			}
		}
		

		
		return itvRepository.save(itv);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		itvRepository.deleteById(id);
	}
}
