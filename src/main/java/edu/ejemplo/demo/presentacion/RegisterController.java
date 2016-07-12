package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UserService;
import edu.ejemplo.demo.presentacion.forms.ForgetPasswordForm;
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

//    @InitBinder
//    private void initBinder(WebDataBinder binder){
//        binder.addValidators(passwordValidator);
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String customerVerifyEmail(Model model){
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(Model model, @Valid UserForm userForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request){

        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        if(bindingResult.hasErrors()){
            ForgetPasswordForm forgetPasswordForm = new ForgetPasswordForm();
            model.addAttribute("flagSignUp", 1);
            model.addAttribute("userForm", userForm);
            model.addAttribute("forgetPasswordForm", forgetPasswordForm);
            return "login";
        }
        try{

            User user = new User();
            BeanUtils.copyProperties(userForm, user);
            userService.saveOrUpdate(user, request, userForm, gRecaptchaResponse);
            String successMsg;
            if(userForm.getId() == null){
                successMsg = messageSource.getMessage("conductor.notif.create.success", new Object[]{user.getNombre()}, locale);
            }else{
                successMsg = messageSource.getMessage("global.notif.success.edit", new Object[]{user.getNombre()}, locale);
            }
            redirectAttributes.addFlashAttribute("successMsgSignUp", successMsg);
            redirectAttributes.addFlashAttribute("flagSignUp", 1);
            return "redirect:/login";
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsgSignUp", e.getMessage());
            model.addAttribute("flagSignUp", 1);
            return "login";
        }
    }

    @RequestMapping(path = "/forget", method = RequestMethod.GET)
    public String customerForgetPassword(Model model){
        model.addAttribute("forgetPasswordForm", new ForgetPasswordForm());
        return "register/forget";
    }

    @RequestMapping(path = "/forget", method = RequestMethod.POST)
    public String forgetPassword(Model model, @Valid ForgetPasswordForm forgetPasswordForm, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            UserForm userForm = new UserForm();
            model.addAttribute("flagForget", 1);
            model.addAttribute("userForm", userForm);
            model.addAttribute("forgetPasswordForm", forgetPasswordForm);
            return "login";
        }
        try{
            User user = userService.forgetPassword(forgetPasswordForm.getForgetPassword(), request);
            Object[] args = {user.getEmail()};
            redirectAttributes.addFlashAttribute("successMsgForget", messageSource.getMessage("success.sent.forget.email", args, null));
            redirectAttributes.addFlashAttribute("flagForget", 1);
            return "redirect:/login";
        }catch (Exception e){
            UserForm userForm = new UserForm();
            LOGGER.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsgForget", e.getMessage());
            model.addAttribute("flagForget", 1);
            model.addAttribute("userForm", userForm);
            return "login";
        }
    }
}