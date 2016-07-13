package edu.ejemplo.demo.negocio.impl;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.converter.EditUserConverter;
import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.model.Role;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.MailService;
import edu.ejemplo.demo.negocio.UserService;
import edu.ejemplo.demo.presentacion.forms.UserForm;
import edu.ejemplo.demo.repositorios.RoleRepository;
import edu.ejemplo.demo.repositorios.UserDataDao;
import edu.ejemplo.demo.repositorios.UserRepository;
import edu.ejemplo.demo.validators.CaptchaValidator;
import edu.ejemplo.demo.validators.SignupValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zulfy on 09/12/15.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger log = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SignupValidator signupValidator;
    @Autowired
    private CaptchaValidator captchaValidator;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserDataDao userDataDao;
    @Autowired
    private EditUserConverter editUserConverter;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class, timeout = 30)
    public User save(User entity, HttpServletRequest request, UserForm userForm, String gRecaptchaResponse) {

        User user = new User();
        boolean sentEmail;
        Boolean validCaptcha;

        //validate form
        signupValidator.validateForSignUp(userForm);

        //check captcha is valid or not valid
        validCaptcha = captchaValidator.verify(gRecaptchaResponse);
        if (!validCaptcha){
            String error = messageSource.getMessage("error.captcha.invalid", null, null);
            throw new BusinessLogicException(error);
        }

            Role roleParking = roleRepository.findOne(Role.ROLE_CONDUCTOR);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleParking);
            user.setRoles(roleSet);
            user.setEmailCode(RandomStringUtils.randomAlphanumeric(6));
            user.setActive(true);
            user.setNombre(userForm.getNombre());
            user.setEmail(userForm.getEmail());
            user.setEmailVerificado(false);
            if(userForm.getPassword() != null  && userForm.getPassword() != ""){
                String hashPassword = passwordEncoder.encode(userForm.getPassword());
                user.setPassword(hashPassword);
            }
            user = userRepository.save(user);
            sentEmail = true;
            log.info("successfully create user " + user.getNombre() + "with email " + user.getEmail() + " and password " + userForm.getPassword());
            
        if(sentEmail){
            mailService.sendRegistrationEmail(user, request);
        }
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class, timeout = 30)
    public User saveOrUpdate(HttpServletRequest request, UserForm userForm) {

        User user = new User();
        boolean sentEmail = false;

        //validate form
        signupValidator.validateForSignUp(userForm);

        //check if new or update
        if(userForm.getId() != null){
            user = userRepository.findById(userForm.getId());
            user.setEmail(userForm.getEmail());
            user.setNombre(userForm.getNombre());
            if (userForm.getPassword() != null){
                String hashPassword = passwordEncoder.encode(userForm.getPassword());
                user.setPassword(hashPassword);
            }
            userRepository.save(user);
        }else{
            Role roleParking = roleRepository.findOne(Role.ROLE_CONDUCTOR);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleParking);
            user.setRoles(roleSet);
            user.setEmailCode(RandomStringUtils.randomAlphanumeric(6));
            user.setNombre(userForm.getNombre());
            user.setEmail(userForm.getEmail());
            user.setActive(true);
            user.setEmailVerificado(false);
            if(userForm.getPassword() != null  && userForm.getPassword() != ""){
                String hashPassword = passwordEncoder.encode(userForm.getPassword());
                user.setPassword(hashPassword);
            }
            user = userRepository.save(user);
            sentEmail = true;
            if(sentEmail){
                mailService.sendRegistrationEmail(user, request);
            }
        }
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60)
    public User resetPassword(User user) {
        User newUser = userRepository.findOneByEmailAndActive(user.getEmail(),true);
        String hashPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(hashPassword);
        newUser.setEmailCode(null);
        newUser.setEmailVerificado(true);
        return userRepository.save(newUser);
    }

    @Override
    public User forgetPassword(String email) {

        if(userRepository.countByEmailAndActive(email.trim(), true) > 0){
            //generate random password
            String password = RandomStringUtils.randomAlphanumeric(8);
            String hashPassword = passwordEncoder.encode(password);
            User user = userRepository.findOneByEmailAndActive(email, true);
            user.setPassword(hashPassword);
            User user2 = userRepository.save(user);

            // send email
            mailService.sendForgetEmail(email, password);
            return user2;
        }else{
            Object[] errorArgs = {email};
            String error = messageSource.getMessage("error.forget.email.not.found", errorArgs, null);
            throw new BusinessLogicException(error);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
    public DataSet<User> findWithDatatablesCriterias(DatatablesCriterias criterias) {
        List<User> userList =  userDataDao.findWithDatatablesCriterias(criterias);
        Long count  = userDataDao.getTotalCount();
        Long countFiltered = userDataDao.getFilteredCount(criterias);
        return new DataSet<>(userList, count, countFiltered);
    }

    @Override
    public UserForm findUserById(Long id) {
        User user = userRepository.findById(id);
        return editUserConverter.convertToEditAndDetail(user);
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id);
        String name = user.getNombre();
        user.setActive(false);
        userRepository.save(user);
        log.info("delete data user " + name + " with email " + user.getEmail() + " done");
        return name;
    }
}