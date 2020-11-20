package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.VehiculoRepository;
import org.springframework.gresur.service.exceptions.MatriculaUnsupportedPatternException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoService {

	private VehiculoRepository vehiculoRepository;

	@Autowired
	public VehiculoService(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Vehiculo> findAll() throws DataAccessException{
		return vehiculoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Vehiculo findById(Long id) throws DataAccessException{
		return vehiculoRepository.findById(id).get();
	}
	
	@Transactional(rollbackFor = MatriculaUnsupportedPatternException.class)
	public Vehiculo add(Vehiculo vehiculo) throws DataAccessException, MatriculaUnsupportedPatternException{
		 TipoVehiculo tipo = vehiculo.getTipoVehiculo();
		 switch(tipo) {
		case CAMION:
			if(!vehiculo.getMatricula().equals("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$")) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido");
			break;
		case CARRETILLA_ELEVADORA:
			if(!vehiculo.getMatricula().equals("^E[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$")) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido");
			break;
		case FURGONETA:
			if(!vehiculo.getMatricula().equals("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$")) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido");
			break;
		case GRUA:
			if(!vehiculo.getMatricula().equals("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$")) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido");
			break;
		default:
			throw new NullPointerException();
		 
		 }
		return vehiculoRepository.save(vehiculo);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		vehiculoRepository.deleteById(id);
	}
	
	
}
