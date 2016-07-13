package edu.ejemplo.demo.negocio;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.presentacion.forms.UserForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zulfy on 07/12/15.
 */
public interface UserService {

    User save(User entity, HttpServletRequest request, UserForm userForm, String gRecaptchaResponse);
    User saveOrUpdate(HttpServletRequest request, UserForm userForm);
    User resetPassword(User user);
    User forgetPassword(String email);
    DataSet<User> findWithDatatablesCriterias(DatatablesCriterias criterias);
    UserForm findUserById(Long id);
    String deleteUser(Long id);
}