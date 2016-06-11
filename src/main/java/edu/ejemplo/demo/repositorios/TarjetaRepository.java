package edu.ejemplo.demo.repositorios;

import org.springframework.data.repository.CrudRepository;

import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.model.User;

public interface TarjetaRepository extends CrudRepository<TarjetaCredito, Long> {
	
}
