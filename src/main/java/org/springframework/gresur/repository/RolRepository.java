package org.springframework.gresur.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.ERol;
import org.springframework.gresur.model.Rol;

public interface RolRepository extends CrudRepository<Rol, Long> {
	Optional<Rol> findByName(ERol name);
}
