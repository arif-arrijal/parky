package edu.ejemplo.demo.presentacion.parking;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.presentacion.RespuestaValidacion;
import edu.ejemplo.demo.repositorios.ParkingRepository;

@Controller
@RequestMapping("/parkings/alta")
public class AltaParkingController {
	
	@Autowired
	private ParkingRepository parkingRepository;

	/*
	@RequestMapping(method = RequestMethod.GET)
	public String alta(Model model) {
		
		model.addAttribute("parking", new Parking());
		model.addAttribute("vista", "alta");
		
		return "layout";
	}
	*/

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public RespuestaValidacion alta(@Valid Parking parking, BindingResult br, Model model
			, RedirectAttributes redirectAttributes) {
		
		if (!br.hasErrors()) {
			parkingRepository.save(parking);
			redirectAttributes.addFlashAttribute("mensaje", "El parking se registro correctamente");
		}
		
		return new RespuestaValidacion(br);
	}
}
