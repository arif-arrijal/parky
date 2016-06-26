package edu.ejemplo.demo.repositorios;

import org.springframework.data.repository.CrudRepository;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.model.User;

public interface CarRepository extends CrudRepository<Coche, Long> {
	
}
