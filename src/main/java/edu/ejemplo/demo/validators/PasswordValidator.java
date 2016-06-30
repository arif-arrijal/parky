package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.presentacion.forms.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by zulfy on 10/12/15.
 */
@Component
public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;
        if (userForm.getId() != null && !userForm.getPassword().equals(userForm.getPassword2())) {
            errors.rejectValue("password2", "error.password.nomatch");
        }
    }
}
