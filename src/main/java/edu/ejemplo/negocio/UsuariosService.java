package edu.ejemplo.negocio;

import edu.ejemplo.demo.excepciones.NoEncontradoException;
import edu.ejemplo.demo.excepciones.UsuarioYaExisteException;
import edu.ejemplo.demo.model.User;

public interface UsuariosService {

	void registrar(User user, String urlBase) throws UsuarioYaExisteException;

	void activar(Long idUsuario, String code) throws NoEncontradoException;

}
