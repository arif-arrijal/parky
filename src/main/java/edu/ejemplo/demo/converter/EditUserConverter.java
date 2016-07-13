package edu.ejemplo.demo.converter;

import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.presentacion.forms.UserForm;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 7/11/2016.
 */
@Component
public class EditUserConverter {

    public UserForm convertToEditAndDetail(User user){
        UserForm form = new UserForm();
        form.setId(user.getId());
        form.setEmail(user.getEmail());
        form.setEmail2(user.getEmail());
        form.setNombre(user.getNombre());
        return form;
    }

}
