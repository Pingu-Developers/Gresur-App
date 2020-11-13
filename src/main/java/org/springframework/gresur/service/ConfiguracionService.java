package org.springframework.gresur.service;

import java.lang.reflect.Field;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.repository.ConfiguracionRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionService {
	
	private ConfiguracionRepository configRepo;
	
	@Autowired
	public ConfiguracionService(ConfiguracionRepository configRepo) {
		this.configRepo = configRepo;
	}
	
	@Transactional
	public Configuracion getConfig() {
		return configRepo.findAll().iterator().next();
	}
	
	@Transactional
	public Configuracion updateConfig(Configuracion config) {
		Configuracion actual = this.getConfig();
		if (config.getMMA() != actual.getMMA()) {
			actual.setMMA(config.getMMA());
		} if (config.getSalarioMinimo() != actual.getSalarioMinimo()) {
			actual.setSalarioMinimo(config.getSalarioMinimo());
		} if (config.getNumMaxNotificaciones() != actual.getNumMaxNotificaciones()) {
			actual.setNumMaxNotificaciones(config.getNumMaxNotificaciones());
		}
		return configRepo.save(actual);
	}
	
	public Double getSalarioMinimo() {
		return this.getConfig().getSalarioMinimo();
	}
	
	public Integer getNumMaxNotificaciones() {
		return this.getConfig().getNumMaxNotificaciones();
	}
	
	public Double getMMA() {
		return this.getConfig().getMMA();
	}
}
