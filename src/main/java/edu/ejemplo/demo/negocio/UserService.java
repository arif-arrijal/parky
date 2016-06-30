package edu.ejemplo.demo.negocio;

import edu.ejemplo.demo.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zulfy on 07/12/15.
 */
public interface UserService {

    User saveOrUpdate(User entity, HttpServletRequest request);
    User resetPassword(User user);
}