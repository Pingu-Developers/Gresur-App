package org.springframework.gresur.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.repository.LineasFacturaRepository;
import org.springframework.transaction.annotation.Transactional;

public class LineasFacturaService {
	
	private LineasFacturaRepository lineasRepo;
	
	@Autowired
	public LineasFacturaService(LineasFacturaRepository lineasRepo) {
		this.lineasRepo = lineasRepo;
	}
	
	
	@Transactional(readOnly = true)
	public List<LineaFactura> findAll(){
		return lineasRepo.findAll();
	}
	
	@Transactional
	public LineaFactura save(LineaFactura linea) {		
		
		LineaFactura tmp = linea;
		LineaFactura otherLinea = lineasRepo.findByFacturaIdAndProductoId(tmp.getFactura().getId(), tmp.getProducto().getId()).orElse(null);

		if(otherLinea != null) {
			otherLinea.setCantidad(otherLinea.getCantidad() + linea.getCantidad());
			linea = otherLinea;
		}
		
		return lineasRepo.save(linea);
	}
	
	@Transactional
	public List<LineaFactura> saveAll(List<LineaFactura> lineas){
		return lineas.stream().map(x->this.save(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public void deleteAll() {
		lineasRepo.deleteAll();
	}

}
