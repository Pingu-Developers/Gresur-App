package org.springframework.gresur.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.repository.ConfiguracionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfiguracionService {
	
	@PersistenceContext
	private EntityManager em;
	
	private ConfiguracionRepository configRepo;
	
	@Autowired
	public ConfiguracionService(ConfiguracionRepository configRepo) {
		this.configRepo = configRepo;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	
	@Transactional
	public Configuracion getConfig() {
		return configRepo.findAll().iterator().next();
	}
	
	@Transactional
	public Configuracion save(Configuracion config) {
		em.clear();

		Configuracion ret = configRepo.save(config);
		em.flush();
		return ret;
	}
	
	public Double getSalarioMinimo() {
		return this.getConfig().getSalarioMinimo();
	}
	
	public Integer getNumMaxNotificaciones() {
		return this.getConfig().getNumMaxNotificaciones();
	}
	
	@Transactional
	public String nextValEmitidas() {
		Configuracion config = this.getConfig();
		config.setFacturaEmitidaSeq(config.getFacturaEmitidaSeq()+1);
		config = this.save(config);
		return "E-" + config.getFacturaEmitidaSeq();
	}
	
	@Transactional
	public String nextValRecibidas() {
		Configuracion config = this.getConfig();
		config.setFacturaRecibidaSeq(config.getFacturaRecibidaSeq()+1);
		config = this.save(config);
		return "R-" + config.getFacturaRecibidaSeq();
	}
	
	@Transactional
	public String nextValEmitidasRectificada() {
		Configuracion config = this.getConfig();
		config.setFacturaEmitidaRectSeq(config.getFacturaEmitidaRectSeq()+1);
		config = this.save(config);
		return "RCTE-" + config.getFacturaEmitidaRectSeq();
	}
	
	@Transactional
	public String nextValRecibidasRectificada() {
		Configuracion config = this.getConfig();
		config.setFacturaRecibidaRectSeq(config.getFacturaRecibidaRectSeq()+1);
		config = this.save(config);
		return "RCTR-" + config.getFacturaRecibidaRectSeq();
	}
	
	@Transactional
	public void deleteAll() {
		configRepo.deleteAll();
	}

}
