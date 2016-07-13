package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UserService;
import edu.ejemplo.demo.presentacion.forms.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by admin on 7/11/2016.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "user/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, @RequestParam(value = "id", required = false) Long id){
        UserForm userForm = new UserForm();
        if (id != null){
            userForm = userService.findUserById(id);
        }
        model.addAttribute("userForm", userForm);
        return "user/detail";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String save(Model model, @Valid UserForm userForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, HttpServletRequest request){

        try{
            User user = userService.saveOrUpdate(request, userForm);
            String successMsg;

            if(userForm.getId() == null){
                Object[] Args = {user.getNombre()};
                successMsg = messageSource.getMessage("success.create.user", Args, null);
            }else{
                Object[] Args = {user.getNombre()};
                successMsg = messageSource.getMessage("success.edit.user", Args, null);
            }
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
            return "redirect:/user/detail?id="+user.getId();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsg", e.getMessage());
            return "user/detail";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String name = userService.deleteUser(id);
        Object[] args = {name};
        redirectAttributes.addFlashAttribute("successMsg", messageSource.getMessage("user.notif.delete", args, locale));
        return  "redirect:/user";
    }
}
