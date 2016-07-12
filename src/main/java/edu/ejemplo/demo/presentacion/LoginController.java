package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.presentacion.forms.ForgetPasswordForm;
import edu.ejemplo.demo.presentacion.forms.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {

    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("forgetPasswordForm", new ForgetPasswordForm());
        return "login";
    }
}