package edu.ejemplo.demo.presentacion.parking;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.presentacion.RespuestaValidacion;
import edu.ejemplo.demo.repositorios.ParkingRepository;
import edu.ejemplo.demo.repositorios.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
 

	@RequestMapping(method = RequestMethod.POST, path="/user/register")
	@ResponseBody
	public RespuestaValidacion alta(@Valid User user, BindingResult br, Model model) {
		if (!br.hasErrors()) {
			user.setEmailCode("random-code");
			user.setEmailVerificado(0);
			userRepository.save(user);
		}
		
		return new RespuestaValidacion(br);
	}
}
