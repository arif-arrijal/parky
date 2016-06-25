package edu.ejemplo.demo.negocio.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.Conductor;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.ConductorService;
import edu.ejemplo.demo.repositorios.ConductorRepository;
import edu.ejemplo.demo.repositorios.UserRepository;

@Service
public class ConductorServiceImpl implements ConductorService {

	@Autowired
	private ConductorRepository conductorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void registrar(User user, Conductor conductor, String urlBase) throws YaExisteException {
		
		if (userRepository.getUserByEmail(user.getEmail()) != null) {
			throw new YaExisteException();
		}
		
		userRepository.save(user);
		conductor.setUsuario(user);
		conductorRepository.save(conductor);
		
		sendConfirmationEmail(user, urlBase);
	}
	
	private void sendConfirmationEmail(User user, String urlBase){


		try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject("Email de confirmacion de tu cuenta en Parky.es.");
			
			String loginUrl = urlBase + "/" + user.getEmailCode();

			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("parkyoviedo@gmail.com");
			helper.setTo(user.getEmail());
			helper.setText("Hola! <br /> Te has regisrado con exito en Parky!."
			    		+ "<br /> Por favor haz click <a href='"+loginUrl+"'>aqui</a> para verificar tu cuenta de correo o introduce el siguiente codigo de activacion en la web al entrar."
			    		+ "<br /> Codigo de verificacion : "+user.getEmailCode(), true);
				mailSender.send(message);
		} catch (MessagingException e1) {
			//en principio mostramos solo una error generico de tiempo de ejcucion. luego lo modificaremos
			throw new RuntimeException(e1);
		}
	        

	        

	}

}
