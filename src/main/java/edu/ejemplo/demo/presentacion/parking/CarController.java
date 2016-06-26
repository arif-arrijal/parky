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
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.presentacion.RespuestaValidacion;
import edu.ejemplo.demo.repositorios.CarRepository;
import edu.ejemplo.demo.repositorios.ParkingRepository;
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
public class CarController {

	@Autowired
	private CarRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/car/register")
	@ResponseBody
	public RespuestaValidacion alta(@Valid Coche car, BindingResult br,
			Model model) {
		if (!br.hasErrors()) {
			userRepository.save(car);
		}

		return new RespuestaValidacion(br,"Coche añadido con exito");
	}

	private static final Logger logger = LoggerFactory
			.getLogger(CarController.class);

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/car/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = request.getContextPath();
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists()){
					dir.mkdirs();
				}
				String name = System.currentTimeMillis()+UUID.randomUUID().toString();
 
				
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				return "{\"message\":\"success\", \"file\": \"" + name+"\"}";
			} catch (Exception e) {
				return "You failed to upload " + e.getMessage();
			}
		} else {
			return "Ha fallado la subida de la foto" 
					+ " porque el archivo estaba vacio";
		}
	}
	String randomString(final int length) {
	    Random r = new Random(); // perhaps make it a class variable so you don't make a new one every time
	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < length; i++) {
	        char c = (char)(r.nextInt((int)(Character.MAX_VALUE)));
	        sb.append(c);
	    }
	    return sb.toString();
	}

}
