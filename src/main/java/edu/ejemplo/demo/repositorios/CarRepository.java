package edu.ejemplo.demo.repositorios;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Coche, Long> {

    Coche findById(Long id);
    Long countByMatricula(String matricula);
    Long countByNombreCoche(String nombreCoche);
    List<Coche> findByConductor(User conductor);

}
