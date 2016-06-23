package edu.ejemplo.demo.presentacion;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.ParkingsService;
import edu.ejemplo.demo.negocio.UsuariosService;
import edu.ejemplo.demo.presentacion.forms.RegistrarConductorForm;
import edu.ejemplo.demo.presentacion.forms.RegistrarParkingForm;
import edu.ejemplo.demo.repositorios.ParkingRepository;
import edu.ejemplo.demo.repositorios.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private ParkingsService parkingsService;
	
	@Autowired
	private UsuariosService usuariosService;

	@RequestMapping(value={"/","/admin"})
	public String index(Model model, Principal principal) {
		/* if (principal == null){
			System.out.println("Usuario no enconrado IndexController");
		} */
		
		if (principal != null) {
			//String name = principal.getName(); //get logged in username
		    //User user = userRepository.getUserByEmail(name);
	    	//model.addAttribute("logInActivated", user.getEmailVerificado());
		}
	    
		Iterable<Parking> parkings = parkingsService.getParkings();
		Iterable<User> usuarios = usuariosService.getUsuarios();
		
		
		//model.addAttribute("parking", new RegistrarParkingForm());
		model.addAttribute("registrarConductorForm", new RegistrarConductorForm());
		//model.addAttribute("car", new Coche());
		//model.addAttribute("parkings", parkings);
		model.addAttribute("registrarParkingForm", new RegistrarParkingForm());
		
		//model.addAttribute("tarjeta", new TarjetaCredito());
		model.addAttribute("vista", "index");
		model.addAttribute("usuarios", usuarios);
		
		return "layout";
	}

}
