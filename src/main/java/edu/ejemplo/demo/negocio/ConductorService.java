package edu.ejemplo.demo.negocio;

import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.Conductor;
import edu.ejemplo.demo.model.User;

public interface ConductorService {

	void registrar(User user, Conductor conductor, String urlBase) throws YaExisteException;
}
