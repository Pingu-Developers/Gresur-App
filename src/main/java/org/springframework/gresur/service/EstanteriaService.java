package org.springframework.gresur.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.repository.EstanteriaRepository;
import org.springframework.gresur.service.exceptions.CapacidadEstanteriaExcededException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstanteriaService{
	
	private EstanteriaRepository estanteriaRepository;
	
	@Autowired
	public EstanteriaService(EstanteriaRepository estanteriaRepository) {
		this.estanteriaRepository = estanteriaRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Estanteria> findAll() throws DataAccessException{
		return estanteriaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Estanteria findById(Long id) throws DataAccessException{
		return estanteriaRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public List<Estanteria> findAllEstanteriaByAlmacen(Long id) throws DataAccessException{
		return estanteriaRepository.findByAlmacenId(id);
	}
	
	//TODO Revisar JSQL todos menos uno mismo(filter(x->!x.getId().equals(estanteria.getId())))
	@Transactional
	public Estanteria save(Estanteria estanteria) throws DataAccessException,CapacidadEstanteriaExcededException{
		Almacen almacen = estanteria.getAlmacen();
		
		if(almacen.getCapacidad()<(findAllEstanteriaByAlmacen(almacen.getId()).stream().filter(x->!x.getId().equals(estanteria.getId()))
				.mapToDouble(x->x.getCapacidad()).sum() + estanteria.getCapacidad())) {
			throw new CapacidadEstanteriaExcededException("Las estanterias exceden la capacidad disponible del almacen");
		}
		
		return estanteriaRepository.save(estanteria);
	}
	
	@Transactional
	public void deleteById(Long id) throws DataAccessException{
		estanteriaRepository.deleteById(id);
	} 
	

}
