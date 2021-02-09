package org.springframework.gresur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Optional<User> findByPersonalNIF(String nif);
	
	Boolean existsByUsername(String username);
	
	List<User> findByUsernameContaining(String username);
	
	void deleteByPersonalNIF(String nif);
}