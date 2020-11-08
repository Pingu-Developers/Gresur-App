package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Entidad;
import org.springframework.gresur.repository.EntidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntidadService {
    
    @Autowired
    private EntidadRepository entidadRepo;
    
    @Transactional
    public Iterable<Entidad> findAll(){
        return entidadRepo.findAll();
    }
    
    @Transactional
    public void add(Entidad e) {
        entidadRepo.save(e);
    }
}