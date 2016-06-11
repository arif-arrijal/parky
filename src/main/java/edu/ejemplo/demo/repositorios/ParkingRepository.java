package edu.ejemplo.demo.repositorios;

import org.springframework.data.repository.CrudRepository;

import edu.ejemplo.demo.model.Parking;

public interface ParkingRepository extends CrudRepository<Parking, Long> {

}
