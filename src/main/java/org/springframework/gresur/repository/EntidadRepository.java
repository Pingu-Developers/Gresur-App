package org.springframework.gresur.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Entidad;

public interface EntidadRepository extends CrudRepository<Entidad, String>{
    
    
}