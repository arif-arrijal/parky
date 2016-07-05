package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.presentacion.forms.UserForm;
import edu.ejemplo.demo.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


@Component
public class SignupValidator {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;

    public void validateForSignUp(UserForm userForm){

        //check nombre
        if (userRepository.countByNombre(userForm.getNombre()) > 0){
            Object[] errorArgs = {userForm.getNombre()};
            String error = messageSource.getMessage("error.nombre.already.registered", errorArgs, null);
            throw new BusinessLogicException(error);
        }

        //check if email not match with confirm email
        if (!userForm.getEmail().equals(userForm.getEmail2())) {
            String error = messageSource.getMessage("error.email.notmatch.with.confirm", null, null);
            throw new BusinessLogicException(error);
        }

        //check if email is already taken
        if (userRepository.countByEmail(userForm.getEmail()) > 0){
            Object[] errorArgs = {userForm.getEmail()};
            String error = messageSource.getMessage("error.email.already.registered", errorArgs, null);
            throw new BusinessLogicException(error);
        }


    }
}
