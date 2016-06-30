package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UserService;
import edu.ejemplo.demo.presentacion.forms.UserForm;
import edu.ejemplo.demo.repositorios.UserRepository;
import edu.ejemplo.demo.validators.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/verify")
public class VerifyController {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(VerifyController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordValidator passwordValidator;
    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(passwordValidator);
    }

    @RequestMapping(value = "/{email}/{verifyCode}", method = RequestMethod.GET)
    public String customerVerifyEmail(Model model, @PathVariable("email") String email, @PathVariable("verifyCode") String verifyCode){

        LOGGER.info("verifying email and verify code");
        User user = userRepository.findOneByEmailAndEmailCode(email, verifyCode);
        if(user == null){
            model.addAttribute("errorMsg", "User not found or invalid verification code");
            return "verification/error";
        } else{
            UserForm userForm = new UserForm();
            BeanUtils.copyProperties(user, userForm);
            userForm.setEmail(email);
            userForm.setEmailCode(verifyCode);
            model.addAttribute("userForm", userForm);
        }

        return "verification/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String userVerifyEmail(@Valid UserForm userForm, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("userForm", userForm);
            model.addAttribute("errorMsgList", result.getAllErrors());
            return "verification/index";
        }
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.resetPassword(user);
        model.addAttribute("statusMessageKey", "Password has been modified");
        return "verification/success";
    }
}