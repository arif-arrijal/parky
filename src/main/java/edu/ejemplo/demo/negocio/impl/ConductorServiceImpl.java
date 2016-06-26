package edu.ejemplo.demo.negocio.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.Conductor;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.ConductorService;
import edu.ejemplo.demo.repositorios.ConductorRepository;
import edu.ejemplo.demo.repositorios.UserRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
public class ConductorServiceImpl implements ConductorService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ConductorServiceImpl.class);

	@Autowired
	private ConductorRepository conductorRepository;

	@Autowired
	private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 30)
	public void registrar(User user, Conductor conductor, HttpServletRequest request) throws YaExisteException {
		
		if (userRepository.getUserByEmail(user.getEmail()) != null) {
			throw new YaExisteException();
		}
		
		userRepository.save(user);
		conductor.setUsuario(user);
		conductorRepository.save(conductor);

		// send email
		String baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toString();
		Context context = new Context();
		context.setVariable("verifyUrl", baseUrl + "/verify/" + user.getEmail() + "/" + user.getEmailCode());
        context.setVariable("verifyCode", user.getEmailCode());
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		String htmlContent = templateEngine.process("email/confirmacion", context);
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			message.setSubject("Email de confirmacion de tu cuenta en Parky.es.");
			message.setFrom(emailSender);
			message.setTo(user.getEmail());
			message.setText(htmlContent, true);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		
	}

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 30)
    public Boolean activate(String email, String verifyCode) {
        User user = userRepository.findOneByEmailAndEmailCode(email, verifyCode);
        if(user == null){
            return false;
        }else{
            userRepository.activate(email);
        }
        return true;
    }
}
