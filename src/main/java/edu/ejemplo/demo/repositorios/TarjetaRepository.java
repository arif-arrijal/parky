package edu.ejemplo.demo.repositorios;

import edu.ejemplo.demo.model.TarjetaCredito;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TarjetaRepository extends CrudRepository<TarjetaCredito, Long> {

    TarjetaCredito findById(Long id);
    Long countByNombreTitular(String holderName);
    Long countByNumeroTarjeta(String creditCardNumber);
    List<TarjetaCredito>  findAll();
	
}
