package edu.ejemplo.demo.configuracion;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class WebConfiguracion {

	
	 @Bean
	    public JavaMailSender javaMailService() {
	        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	        javaMailSender.setJavaMailProperties(getMailProperties());
	        javaMailSender.setUsername("parkyoviedo@gmail.com");
	        javaMailSender.setPassword("uNioVi16");
	        return javaMailSender;
	    }

	    private Properties getMailProperties() {
	        Properties properties = new Properties();
	        properties.setProperty("mail.transport.protocol", "smtp");
	        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
	        properties.setProperty("mail.smtp.port", "587");
	        properties.setProperty("username", "parkyoviedo@gmail.com");
	        properties.setProperty("password", "uNioVi16");
	        properties.setProperty("mail.smtp.auth", "true");
	        properties.setProperty("mail.smtp.starttls.enable", "true");
	        return properties;
	    }
}
