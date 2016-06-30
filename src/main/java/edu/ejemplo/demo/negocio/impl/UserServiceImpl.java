package edu.ejemplo.demo.negocio.impl;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.Role;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UserService;
import edu.ejemplo.demo.repositorios.RoleRepository;
import edu.ejemplo.demo.repositorios.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zulfy on 09/12/15.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String emailSender;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class, timeout = 30)
    public User saveOrUpdate(User entity, HttpServletRequest request) {
        User user;
        boolean sentEmail = false;

        if(entity.getId() != null){
            user = userRepository.findOne(entity.getId());
            BeanUtils.copyProperties(entity, user);
        }else{
            Role roleParking = roleRepository.findOne(Role.ROLE_CONDUCTOR);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleParking);
            user = entity;
            user.setEmailCode(RandomStringUtils.randomAlphanumeric(6));
            user.setPassword(null);
            user.setEmailVerificado(false);
            user.setRoles(roleSet);
            sentEmail = true;
        }

        if(entity.getPassword() != null  && entity.getPassword() != ""){
            String hashPassword = passwordEncoder.encode(entity.getPassword());
            user.setPassword(hashPassword);
        }

        user = userRepository.save(user);

        if(sentEmail){
            // send email
            String baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toString();
            Context context = new Context();
            context.setVariable("verifyCode", user.getEmailCode());
            context.setVariable("verifyUrl", baseUrl + "/verify/" + user.getEmail() + "/" + user.getEmailCode());
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

        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60)
    public User resetPassword(User user) {
        User newUser = userRepository.findOneByEmail(user.getEmail());
        String hashPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(hashPassword);
        newUser.setEmailCode(null);
        newUser.setEmailVerificado(true);
        return userRepository.save(newUser);
    }
}