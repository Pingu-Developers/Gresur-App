package org.springframework.gresur.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.repository.LineasFacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineasFacturaService {

	@PersistenceContext
	private EntityManager em;
	
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
	public void deleteAll() {
		this.lineasRepo.deleteAll();
	}
	
	@Transactional
	public void deleteAll(Iterable<LineaFactura> facturas) {
		this.lineasRepo.deleteAll(facturas);
	}
	
	@Transactional
	public void deleteByFacturaId(Long id) {
		this.lineasRepo.deleteByFacturaId(id);
	}
	
	@Transactional
	public LineaFactura save(LineaFactura linea) {		
		LineaFactura otherLinea = lineasRepo.findByFacturaIdAndProductoId(linea.getFactura().getId(), linea.getProducto().getId()).orElse(null);
		if(otherLinea != null && linea.getId()==null) {
			otherLinea.setCantidad(otherLinea.getCantidad() + linea.getCantidad());
			otherLinea.setPrecio(otherLinea.getPrecio() + linea.getPrecio());
			linea = otherLinea;
		}
		LineaFactura ret = lineasRepo.save(linea);
		em.flush();
		return ret;
	}
	
	@Transactional
	public List<LineaFactura> saveAll(List<LineaFactura> lineas){
		return lineas.stream().map(x->this.save(x)).collect(Collectors.toList());
	}
}
