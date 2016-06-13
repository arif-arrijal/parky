package edu.ejemplo.demo.presentacion.parking;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public RespuestaValidacion alta(@Valid User user, BindingResult br, Model model, HttpServletRequest request) {
		if(!br.hasErrors() && userRepository.getUserByEmail(user.getEmail())!= null){
			//emailAlreadyRegistered
			br.addError(new ObjectError("user", new String[]{"alreadyRegistered"}, new String[]{"email"}, "Email Already Exists."));
		}
		String loginUrl = request.getRemoteAddr()+ "/" ;
		Random r = new Random();
		String code = (r.nextInt(9999-1000) + 1000)+"";
		if (!br.hasErrors()) {
			user.setEmailCode(code);
			user.setEmailVerificado(0);
			userRepository.save(user);
			sendConfirmationEmail(user.getNombre(), user.getEmail(), user.getPassword(), code, loginUrl);
		}
		
		return new RespuestaValidacion(br);
	}
/*	@RequestMapping("/admin")
	public String adminPage(){
		return "layout";
	}
*/	
	@RequestMapping("/403")
	public String error403(){
		return "403";
	}
	
	
	@RequestMapping(value="/login",method = {RequestMethod.GET})
    public String login(@RequestParam(value = "url", required = false, defaultValue = "/") String name) {
    	return "login_global";

    }
	
	@RequestMapping(value="/user/activate",method = {RequestMethod.POST})
	@ResponseBody
    public String activateUser(@RequestParam(value = "code", required = true) String code) {
		
		User user = userRepository.getUserByEmailCode(code);
		if(user == null){
			return "{\"errors\":[{\"field\":\"code\", \"defaultMessage\":\"Invalid code\"}]}";
		}
		user.setEmailconfirm(user.getEmail());
		user.setEmailVerificado(1);
		userRepository.save(user);
		return "{\"resultado\":\"EXITO\"}";
		
		
    }
	 @Autowired
	    private JavaMailSender mailSender;
	
	private void sendConfirmationEmail(String name, String email, String password, String code, String loginUrl){
//		 SimpleMailMessage message = new SimpleMailMessage();
		 
		 try {
			 MimeMessage message = mailSender.createMimeMessage();
			 message.setSubject("Account Confirmation Email.");
	        MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("parkyoviedo@gmail.com");
            helper.setTo(email);
				helper.setText("Hello!! <br /> You have been successfully registered."
			    		+ "<br /> Please click <a href='"+loginUrl+"'>here</a> to login with following credentials."
			    		+ "<br /> Email: "+email
			    		+ "<br /> Password: "+password
			    		+ "<br /> Use authentication code : "+code+" to activate your account.", true);
				mailSender.send(message);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	        

	        

	}
}
