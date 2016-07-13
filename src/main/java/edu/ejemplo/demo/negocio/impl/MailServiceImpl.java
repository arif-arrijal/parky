package edu.ejemplo.demo.negocio.impl;

import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 7/12/2016.
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final Logger log = (Logger) LoggerFactory.getLogger(MailServiceImpl.class);


    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String emailSender;
    @Autowired
    private MessageSource messageSource;

    @Override
    public void sendRegistrationEmail(User user, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toString();
        Context context = new Context();
        context.setVariable("verifyCode", user.getEmailCode());
        context.setVariable("verifyUrl", baseUrl + "/verify/" + user.getEmail() + "/" + user.getEmailCode());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String htmlContent = templateEngine.process("email/confirmacion", context);
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(messageSource.getMessage("label.mail.title.signup", null, null));
            message.setFrom(emailSender);
            message.setTo(user.getEmail());
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void sendForgetEmail(String email, String password) {
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("password", password);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String htmlContent = templateEngine.process("email/forget", context);
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(messageSource.getMessage("label.mail.title.forget.password", null, null));
            message.setFrom(emailSender);
            message.setTo(email);
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            log.info("successfully send forget email to " + email + " , new password is " + password);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }
}
