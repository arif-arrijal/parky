package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.model.User;
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

        if (userForm.getId() == null){
            //check nombre
            if (userRepository.countByNombre(userForm.getNombre()) > 0){
                Object[] errorArgs = {userForm.getNombre()};
                String error = messageSource.getMessage("error.nombre.already.registered", errorArgs, null);
                throw new BusinessLogicException(error);
            }
            //check if email is already taken
            if (userRepository.countByEmailAndActive(userForm.getEmail(),true) > 0){
                Object[] errorArgs = {userForm.getEmail()};
                String error = messageSource.getMessage("error.email.already.registered", errorArgs, null);
                throw new BusinessLogicException(error);
            }
            //check if password not empty
            if (userForm.getPassword().isEmpty() || userForm.getPassword() == null){
                String error = messageSource.getMessage("error.password.may.not.empty", null, null);
                throw new BusinessLogicException(error);
            }
        }else {
            User existingUser = userRepository.findById(userForm.getId());

            //check if email is already taken
            if (existingUser.getEmail().equals(userForm.getEmail())){
            }else {
                if (userRepository.countByEmailAndActive(userForm.getEmail(),true) > 0){
                    Object[] errorArgs = {userForm.getEmail()};
                    String error = messageSource.getMessage("error.email.already.registered", errorArgs, null);
                    throw new BusinessLogicException(error);
                }
            }

            //check nombre
            if (existingUser.getNombre().equals(userForm.getNombre())){
            }else {
                if (userRepository.countByNombre(userForm.getNombre()) > 0){
                    Object[] errorArgs = {userForm.getNombre()};
                    String error = messageSource.getMessage("error.nombre.already.registered", errorArgs, null);
                    throw new BusinessLogicException(error);
                }
            }
        }

        //check if email not match with confirm email
        if(userForm.getEmail().equals(userForm.getEmail2())) {
        }else{
            String error = messageSource.getMessage("error.email.notmatch.with.confirm", null, null);
            throw new BusinessLogicException(error);
        }

    }
}
