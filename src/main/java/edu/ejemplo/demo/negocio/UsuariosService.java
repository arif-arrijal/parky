package edu.ejemplo.demo.negocio;

import edu.ejemplo.demo.excepciones.NoEncontradoException;
import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.User;

public interface UsuariosService {
	
	Iterable<User> getUsuarios();
	
	void registrar(User user, String urlBase) throws YaExisteException;

	void activar(Long idUsuario, String code) throws NoEncontradoException;

}
