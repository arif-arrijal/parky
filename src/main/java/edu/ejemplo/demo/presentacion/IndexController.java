package edu.ejemplo.demo.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.repositorios.ParkingRepository;

@Controller
public class IndexController {
	
	@Autowired
	private ParkingRepository parkingRepository;

	@RequestMapping("/")
	public String index(Model model) {
		
		Iterable<Parking> parkings = parkingRepository.findAll();
		
		model.addAttribute("parking", new Parking());
		model.addAttribute("user", new User());
		model.addAttribute("car", new Coche());
		model.addAttribute("parkings", parkings);
		model.addAttribute("tarjeta", new TarjetaCredito());
		model.addAttribute("vista", "index");
		
		return "layout";
	}

}
