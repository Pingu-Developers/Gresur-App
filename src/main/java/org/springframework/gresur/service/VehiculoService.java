package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import org.omg.CORBA.portable.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.VehiculoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.gresur.service.exceptions.MatriculaUnsupportedPatternException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoService {

	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private ITVService ITVService;
	
	@Autowired
	private SeguroService seguroService;
	

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
	public Vehiculo save(Vehiculo vehiculo) throws DataAccessException, MatriculaUnsupportedPatternException{
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
		 
		 //TODO La validacion debe comprobar si la ultima ITV es válida (es decir, exits y no es NEGATIVA o DESFAVORABLE)
		 // y debe lanzar una excepción en lugar de setearlo a false (o lanzar un warnig en un popup avisando de que no puede ser true y se
		 //pondra automaticamente a false). Lo mismo para los seguros
		 if(ITVService.findByVehiculo(vehiculo.getId()).size()==0 && vehiculo.getDisponibilidad()==true){
			 vehiculo.setDisponibilidad(false);
		 }
		 
		 if(seguroService.findByVehiculo(vehiculo.getId()).size()==0 && vehiculo.getDisponibilidad()==true){
			 vehiculo.setDisponibilidad(false);
		 }
		 
		return vehiculoRepository.save(vehiculo);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		vehiculoRepository.deleteById(id);
	}
	
	/* USER STORIES*/
	@Transactional(readOnly = true)
	public ITV getUltimaITV(Vehiculo v) throws DataAccessException{
		return ITVService.findByVehiculo(v.getId()).stream().max(Comparator.naturalOrder()).get();
	}
	
	@Transactional(readOnly = true)
	public Seguro getUltimoSeguro(Vehiculo v) throws DataAccessException{
		return seguroService.findByVehiculo(v.getId()).stream().max(Comparator.naturalOrder()).get();
	}
	
	// Validacion de la ITV y Seguros de los vehículos
	@Scheduled(cron = "0 7 * * * *")
	@Transactional(readOnly = true)
	public void ITVSegurovalidation() throws UnknownException{
		Iterator<Vehiculo> vehiculos = vehiculoRepository.findAll().iterator();
		
		while (vehiculos.hasNext()) {
			
			Vehiculo v = vehiculos.next();
			ITV ultimaITV = this.getUltimaITV(v);
			Seguro ultimoSeguro = this.getUltimoSeguro(v);
			
			if(ultimaITV.getResultado()==ResultadoITV.NEGATIVA || ultimaITV.getResultado()==ResultadoITV.DESFAVORABLE) {
				v.setDisponibilidad(false);
				vehiculoRepository.save(v);
				Notificacion warning = new Notificacion();
				warning.setCuerpo("El vehículo con matrícula: " + v.getMatricula() + "ha dejado de estar disponible debido a la invalidez de su ITV");
				warning.setTipoNotificacion(TipoNotificacion.SISTEMA);
				try {
					notificacionService.save(warning);
				} catch (Exception e) {
					throw new UnknownException(e);
				}
			}
			if(ultimoSeguro == null || ultimoSeguro.getFechaExpiracion().isBefore(LocalDate.now())) {
				v.setDisponibilidad(false);
				vehiculoRepository.save(v);
				Notificacion warning = new Notificacion();
				warning.setCuerpo("El vehículo con matrícula: " + v.getMatricula() + "ha dejado de estar disponible debido a la caducidad de su Seguro");
				warning.setTipoNotificacion(TipoNotificacion.SISTEMA);
				try {
					notificacionService.save(warning);
				} catch (Exception e) {
					throw new UnknownException(e);
				}
			}
		}
	}
}
