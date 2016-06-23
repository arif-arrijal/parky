package edu.ejemplo.demo.negocio;

import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.Parking;

public interface ParkingsService {

	Iterable<Parking> getParkings();
	static void registrar(Parking parking) throws YaExisteException{
			
		
	}
	
}
