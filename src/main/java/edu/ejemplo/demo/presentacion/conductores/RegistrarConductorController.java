package edu.ejemplo.demo.presentacion.conductores;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.negocio.ConductorService;
import edu.ejemplo.demo.presentacion.RespuestaValidacion;
import edu.ejemplo.demo.presentacion.forms.RegistrarConductorForm;

@Controller
public class RegistrarConductorController {

	@Autowired
	private ConductorService conductorService;
	
	@RequestMapping(value = "/conductores", method = RequestMethod.POST)
	@ResponseBody
	public RespuestaValidacion registrar(@Valid RegistrarConductorForm form, BindingResult br, HttpServletRequest request) {
		
		if (!br.hasErrors()) {
			try {
				conductorService.registrar(form.getUser(), form.getConductor(), request);
			} catch (YaExisteException e) {
				br.addError(new FieldError("registrarConductorForm", "email", "EMail repetido"));
			}
		}
		
		return new RespuestaValidacion(br, "Te registraste con exito, por favor revisa tu correo para verificar tu dirección");
	}
}
