package edu.ejemplo.demo.negocio.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.ejemplo.demo.excepciones.NoEncontradoException;
import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.GeneradorClaves;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UsuariosService;
import edu.ejemplo.demo.repositorios.UserRepository;

@Service
public class UsuarioServiceImpl implements UsuariosService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//		UserDetails ud = userRepository.getUserByEmail(email);
//		if (ud == null) {
//			throw new UsernameNotFoundException("Usuario no encontrado");
//		}
//		return ud;
		return null;
	}
	
	@Override
	@Transactional
	// Para que el método se ejecute completo (si hay algun error se hace rollback de todos los cambios en BD).
	public void registrar(User user, String urlBase) throws YaExisteException {
		
		if(userRepository.getUserByEmail(user.getEmail())!= null){
			throw new YaExisteException();
		}
		
		//generamos la clave de activacion usando la funcion que tenemos de la clase generadora de claves
		user.setEmailCode(new GeneradorClaves().generarApiKey(5));
		user.setEmailVerificado(false);
		userRepository.save(user);
		sendConfirmationEmail(user, urlBase);
	}
	
	@Override
	@Transactional
	public void activar(Long idUsuario, String code) throws NoEncontradoException {
		
		User user = userRepository.findOne(idUsuario);
		if (user == null) {
			throw new NoEncontradoException();
		}
		
		user.setEmailVerificado(true);
	}

	private void sendConfirmationEmail(User user, String urlBase){


		try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject("Confirma tu cuenta de correo en Parky.es");
			
			String loginUrl = urlBase + "/" + user.getEmailCode();

			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("parkyoviedo@gmail.com");
			helper.setTo(user.getEmail());
			helper.setText("Hola! <br /> Te has regisrado con éxito."
			    		+ "<br /> Por favor haz click <a href='"+loginUrl+"'>aqui</a> para verificar tu email"
			    		+ "<br /> Email: "+user.getEmail()
			    		+ "<br /> Codigo de autenficacion  "+user.getEmailCode()+" para activar tu cuenta.", true);
				mailSender.send(message);
		} catch (MessagingException e1) {
			//en principio mostramos solo una error generico de tiempo de ejcucion. luego lo modificaremos
			throw new RuntimeException(e1);
		}
	        

	        

	}

	@Override
	public Iterable<User> getUsuarios() {
		return userRepository.findAll();
	}
	
}
