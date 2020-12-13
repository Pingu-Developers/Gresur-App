package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.omg.CORBA.portable.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.VehiculoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.gresur.service.exceptions.MatriculaUnsupportedPatternException;
import org.springframework.gresur.service.exceptions.VehiculoIllegalException;
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
	private AdministradorService adminService;
	

	@Autowired
	public VehiculoService(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
		
	}
	
	@Transactional(readOnly = true)
	public Iterable<Vehiculo> findAll() throws DataAccessException{
		return vehiculoRepository.findAll();
	}
	@Transactional(readOnly = true)
	public Vehiculo findByMatricula(String matricula) throws DataAccessException{
		return vehiculoRepository.findByMatricula(matricula);
	}
	
	@Transactional(readOnly = true)
	public Vehiculo findById(Long id) throws DataAccessException{
		return vehiculoRepository.findById(id).get();
	}

	
	//TODO COMPROBAR TODOS LOS PATRONES. NO SE VALIDAN CON .equals, SE VALIDAN CON Pattern.matches
	@Transactional(rollbackFor = MatriculaUnsupportedPatternException.class)
	public Vehiculo save(Vehiculo vehiculo) throws DataAccessException, MatriculaUnsupportedPatternException, VehiculoIllegalException{
		 TipoVehiculo tipo = vehiculo.getTipoVehiculo();
		 switch(tipo) {
		case CAMION:
			if(!Pattern.matches("[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}", vehiculo.getMatricula())) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido para camion");
			break;
		case CARRETILLA_ELEVADORA:
			if(!Pattern.matches("E[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}", vehiculo.getMatricula())) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido para carretilla elevadora");
			break;
		case FURGONETA:
			if(!Pattern.matches("[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}", vehiculo.getMatricula())) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido para furgoneta");
			break;
		case GRUA:
			if(!!Pattern.matches("[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}", vehiculo.getMatricula())) throw new MatriculaUnsupportedPatternException("Formato de matricula no valido para grua");
			break;
		default:
			throw new NullPointerException();
		 }
		 	 
		 ITV ultimaITV = ITVService.findLastITVFavorableByVehiculo(vehiculo.getId());
		 if((ultimaITV == null || ultimaITV.getResultado() != ResultadoITV.FAVORABLE) && vehiculo.getDisponibilidad()==true){
			 throw new VehiculoIllegalException("No valido el vehiculo disponible sin ITV valida");
		 }
		 //TODO Hay que revisar el findByVehiculoIdAndFechaExpiracionAfter de Seguro porque sino no va a ir
//		 Seguro ultimoSeguro = seguroService.findLastSeguroByVehiculo(vehiculo.getId());
//		 if(ultimoSeguro == null && vehiculo.getDisponibilidad()==true){
//			 throw new VehiculoIllegalException("No valido el vehiculo disponible sin Seguro");
//		 }
//		 
		return vehiculoRepository.save(vehiculo);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		vehiculoRepository.deleteById(id);
	}
	@Transactional(readOnly = true)
	public void deleteByMatricula(String matricula) throws DataAccessException{
		 vehiculoRepository.deleteByMatricula(matricula);
	}
	
	@Transactional
	public void deleteAll() throws DataAccessException{
		vehiculoRepository.deleteAll();
	}
	
	
	// Validacion de la ITV y Seguros de los vehículos
	@Scheduled(cron = "0 7 * * * *")
	@Transactional(readOnly = true)
	public void ITVSegurovalidation() throws UnknownException{
		Iterator<Vehiculo> vehiculos = vehiculoRepository.findAll().iterator();
		
		while (vehiculos.hasNext()) {
			
			Vehiculo v = vehiculos.next();
			ITV ultimaITV = ITVService.findLastITVFavorableByVehiculo(v.getId());
			Seguro ultimoSeguro = seguroService.findLastSeguroByVehiculo(v.getId());
			
			if(ultimaITV == null || ultimaITV.getResultado()==ResultadoITV.NEGATIVA || ultimaITV.getResultado()==ResultadoITV.DESFAVORABLE) {
				v.setDisponibilidad(false);
				vehiculoRepository.save(v);
				Notificacion warning = new Notificacion();
				warning.setCuerpo("El vehículo con matrícula: " + v.getMatricula() + "ha dejado de estar disponible debido a la invalidez de su ITV");
				warning.setTipoNotificacion(TipoNotificacion.SISTEMA);
				try {
					List<Personal> lPer = new ArrayList<>();
					for (Administrador adm : adminService.findAll()) {
						lPer.add(adm);
					}
					notificacionService.save(warning,lPer);
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
					List<Personal> lPer = new ArrayList<>();
					for (Administrador adm : adminService.findAll()) {
						lPer.add(adm);
					}
					notificacionService.save(warning,lPer);
				} catch (Exception e) {
					throw new UnknownException(e);
				}
			}
		}
	}
	@Transactional
	public Long count() {
		return vehiculoRepository.count();
	}
}
