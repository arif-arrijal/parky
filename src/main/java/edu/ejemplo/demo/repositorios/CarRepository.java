package edu.ejemplo.demo.repositorios;

import edu.ejemplo.demo.model.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Coche, Long> {

    Coche findById(Long id);
    Long countByMatricula(String matricula);
    Long countByNombreCoche(String nombreCoche);

}
