package edu.ejemplo.demo.negocio;

import edu.ejemplo.demo.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 7/12/2016.
 */
public interface MailService {

    void sendRegistrationEmail(User user, HttpServletRequest request);
    void sendForgetEmail(String email, String password);
}
