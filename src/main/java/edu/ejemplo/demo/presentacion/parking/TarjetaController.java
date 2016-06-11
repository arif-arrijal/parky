package edu.ejemplo.demo.presentacion.parking;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.presentacion.RespuestaValidacion;
import edu.ejemplo.demo.repositorios.CarRepository;
import edu.ejemplo.demo.repositorios.ParkingRepository;
import edu.ejemplo.demo.repositorios.TarjetaRepository;
import edu.ejemplo.demo.repositorios.UserRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TarjetaController {

	@Autowired
	private TarjetaRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/tarjeta/register")
	@ResponseBody
	public RespuestaValidacion alta(@Valid TarjetaCredito tarjeta, BindingResult br,
			Model model) {
		if (!br.hasErrors()) {
			userRepository.save(tarjeta);
		}

		return new RespuestaValidacion(br);
	}
 }
