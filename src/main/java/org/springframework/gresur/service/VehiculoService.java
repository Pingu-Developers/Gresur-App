package org.springframework.gresur.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.VehiculoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoService {

	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	private NotificacionService notificacionService;

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
	
	@Transactional
	public Vehiculo add(Vehiculo vehiculo) throws DataAccessException{
		return vehiculoRepository.save(vehiculo);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		vehiculoRepository.deleteById(id);
	}
	
	/* USER STORIES*/
	@Transactional(readOnly = true)
	public ITV getUltimaITV(Vehiculo v) throws DataAccessException{
		return v.getITVs().stream().max(Comparator.naturalOrder()).get();
	}
	
	@Transactional(readOnly = true)
	public Seguro getUltimoSeguro(Vehiculo v) throws DataAccessException{
		return v.getSeguros().stream().max(Comparator.naturalOrder()).get();
	}
	
	// Validacion de la ITV y Seguros de los vehículos
	@Scheduled(cron = "0 7 * * * *")
	@Transactional(readOnly = true)
	public void ITVSegurovalidation() {
		Iterator<Vehiculo> vehiculos = vehiculoRepository.findAll().iterator();
		
		while (vehiculos.hasNext()) {
			
			Vehiculo v = vehiculos.next();
			ITV ultimaITV = this.getUltimaITV(v);
			Seguro ultimoSeguro = this.getUltimoSeguro(v);
			
			if(ultimaITV.getResultado()==ResultadoITV.NEGATIVA || ultimaITV.getResultado()==ResultadoITV.DESFAVORABLE) {
				v.setDisponibilidad(false);
				Notificacion warning = new Notificacion();
				warning.setCuerpo("El vehículo con matrícula: " + v.getMatricula() + "ha dejado de estar disponible debido a la invalidez de su ITV");
				warning.setTipoNotificacion(TipoNotificacion.SISTEMA);
				notificacionService.add(warning);
			}
			if(ultimoSeguro == null || ultimoSeguro.getFechaExpiracion().isBefore(LocalDate.now())) {
				v.setDisponibilidad(false);
				Notificacion warning = new Notificacion();
				warning.setCuerpo("El vehículo con matrícula: " + v.getMatricula() + "ha dejado de estar disponible debido a la caducidad de su Seguro");
				warning.setTipoNotificacion(TipoNotificacion.SISTEMA);
				notificacionService.add(warning);
			}
		}
	}
}
