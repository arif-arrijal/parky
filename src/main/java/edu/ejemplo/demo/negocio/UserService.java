package edu.ejemplo.demo.negocio;

import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.presentacion.forms.UserForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zulfy on 07/12/15.
 */
public interface UserService {

    User saveOrUpdate(User entity, HttpServletRequest request, UserForm userForm, String gRecaptchaResponse);
    User resetPassword(User user);
}