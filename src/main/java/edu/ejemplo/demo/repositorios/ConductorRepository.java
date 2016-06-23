package edu.ejemplo.demo.repositorios;

import org.springframework.data.repository.CrudRepository;

import edu.ejemplo.demo.model.Conductor;

public interface ConductorRepository extends CrudRepository<Conductor, Long> {

}
