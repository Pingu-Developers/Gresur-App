package org.springframework.gresur.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.LineaEnviado;
import org.springframework.gresur.repository.LineasEnviadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineasEnviadoService {

	private LineasEnviadoRepository lineasEnviadoRepo;
	
	@Autowired
	public LineasEnviadoService(LineasEnviadoRepository lineasEnviadoRepo) {
		this.lineasEnviadoRepo = lineasEnviadoRepo;
	}
	
	@Transactional(readOnly = true)
	public List<LineaEnviado> findAll(){
		return lineasEnviadoRepo.findAll();
	}
	
	@Transactional
	public LineaEnviado save(LineaEnviado l) {
		return lineasEnviadoRepo.save(l);
	}
	
	@Transactional Iterable<LineaEnviado> saveAll(List<LineaEnviado> list){
		return lineasEnviadoRepo.saveAll(list);
	}
	
}
