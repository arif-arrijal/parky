//package edu.ejemplo.demo.presentacion.parking;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import edu.ejemplo.demo.excepciones.YaExisteException;
//import edu.ejemplo.demo.negocio.ConductorService;
//import edu.ejemplo.demo.negocio.ParkingsService;
//import edu.ejemplo.demo.presentacion.RespuestaValidacion;
//import edu.ejemplo.demo.presentacion.forms.RegistrarConductorForm;
//import edu.ejemplo.demo.presentacion.forms.RegistrarParkingForm;
//
//@Controller
//public class RegistrarParkingController {
//
//
//	@Autowired
//	private ParkingsService parkingsService ;
//
//	@RequestMapping(value = "/parkings", method = RequestMethod.POST)
//	@ResponseBody
//	public RespuestaValidacion registrar(@Valid RegistrarParkingForm form, BindingResult br) {
//
//		if (!br.hasErrors()) {
//			try {
//				ParkingsService.registrar(form.getUser(), form.getParking());
//			} catch (YaExisteException e) {
//				br.addError(new FieldError("registrarParkingForm", "email", "Email repetido"));
//			}
//		}
//
//		return new RespuestaValidacion(br, "Parking Registrado con Exito en el Sistema");
//	}
//}
