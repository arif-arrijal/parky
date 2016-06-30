package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UserService;
import edu.ejemplo.demo.presentacion.forms.ParkingForm;
import edu.ejemplo.demo.presentacion.forms.UserForm;
import edu.ejemplo.demo.validators.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by zulfy on 30/06/16.
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private MessageSource messageSource;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(passwordValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String customerVerifyEmail(Model model){
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(Model model, @Valid UserForm userForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            model.addAttribute("userForm", userForm);
            return "register";
        }
        try{
            User user = new User();
            BeanUtils.copyProperties(userForm, user);
            userService.saveOrUpdate(user, request);
            String successMsg;
            if(userForm.getId() == null){
                successMsg = messageSource.getMessage("conductor.notif.create.success", new Object[]{user.getNombre()}, locale);
            }else{
                successMsg = messageSource.getMessage("global.notif.success.edit", new Object[]{user.getNombre()}, locale);
            }
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
            return "redirect:/login";
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsg", e.getMessage());
            return "register";
        }
    }
}