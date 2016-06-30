package edu.ejemplo.demo.presentacion.parking;

import edu.ejemplo.demo.excepciones.NoEncontradoException;
import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UsuariosService;
import edu.ejemplo.demo.presentacion.RespuestaValidacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	private UsuariosService usuariosService;
 

	@RequestMapping(method = RequestMethod.POST, path="/user/register")
	@ResponseBody
	public RespuestaValidacion alta(@Valid User user, BindingResult br, Model model, HttpServletRequest request) {
		
		if (!br.hasErrors()) {
			try {
				usuariosService.registrar(user, request.getRemoteAddr());
			} catch (YaExisteException e) {
				br.addError(new FieldError("user", "email", "alreadyRegistered"));
			}
		}
		
		return new RespuestaValidacion(br, "MENSAJE EXITO USUARIO");
	}
	
/*	@RequestMapping("/admin")
	public String adminPage(){
		return "layout";
	}
*/	
	@RequestMapping("/403")
	public String error403(){
		return "403";
	}
	
	
//	@RequestMapping(value="/login",method = {RequestMethod.GET})
//    public String login(@RequestParam(value = "url", required = false, defaultValue = "/") String name) {
//    	return "login_global";
//
//    }
	
	@RequestMapping(value="/user/{id}/activate/{code}",method = {RequestMethod.POST})
	@ResponseBody
    public RespuestaValidacion activateUser(@PathVariable("id") Long idUsuario, @PathVariable("code") String code) {

		try {
			usuariosService.activar(idUsuario, code);
			return new RespuestaValidacion(true);
		}
		catch (NoEncontradoException e) {
			return new RespuestaValidacion(false);
		}
		
		
    }

}
