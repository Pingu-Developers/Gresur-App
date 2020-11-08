package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Entidad;
import org.springframework.gresur.service.EntidadService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/entidad")
public class EntidadController {
    
    @Autowired
    private EntidadService entidadService;
    
    @GetMapping()
    public Iterable<Entidad> findAll(){
        return entidadService.findAll();
    }
    
    @PostMapping(value = "/addEntidad")
    public void add(@RequestBody Entidad e) {
        entidadService.add(e);
    }
}