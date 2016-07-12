package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.Stay;
import edu.ejemplo.demo.negocio.StayService;
import edu.ejemplo.demo.presentacion.forms.StayForm;
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
@RequestMapping(value = "/stay")
public class StayController {

    private Logger log = LoggerFactory.getLogger(StayController.class);

    @Autowired
    private StayService stayService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "stay/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, @RequestParam(value = "id", required = false) Long id){
        StayForm stayForm = new StayForm();
        if (id != null){
            stayForm = stayService.findStayById(id);
        }
        model.addAttribute("stayForm", stayForm);
        return "stay/detail";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String save(Model model, @Valid StayForm stayForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            model.addAttribute("stayForm", stayForm);
            return "stay/detail";
        }
        try{
            Stay stay = stayService.saveOrUpdate(stayForm, request);
            String successMsg;

            if(stayForm.getId() == null){
                Object[] Args = {stay.getName()};
                successMsg = messageSource.getMessage("success.create.stay", Args, null);
            }else{
                Object[] Args = {stay.getName()};
                successMsg = messageSource.getMessage("success.edit.stay", Args, null);
            }
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
            return "redirect:/stay/detail?id="+stay.getId();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsg", e.getMessage());
            return "stay/detail";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String name = stayService.deleteStay(id);
        Object[] args = {name};
        redirectAttributes.addFlashAttribute("successMsg", messageSource.getMessage("stay.notif.delete", args, locale));
        return  "redirect:/stay";
    }
}
