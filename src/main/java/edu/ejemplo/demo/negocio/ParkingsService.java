package edu.ejemplo.demo.negocio;

import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.User;

public interface ParkingsService {

	Iterable<Parking> getParkings();
	static void registrar(User user, Parking parking) throws YaExisteException{
				
	}
	
}
