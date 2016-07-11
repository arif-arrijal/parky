package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.Province;
import edu.ejemplo.demo.negocio.ProvinceService;
import edu.ejemplo.demo.presentacion.forms.ProvinceForm;
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
@RequestMapping(value = "/province")
public class ProvinceController {

    private Logger log = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "province/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, @RequestParam(value = "id", required = false) Long id){
        ProvinceForm provinceForm = new ProvinceForm();
        if (id != null){
            provinceForm = provinceService.findProvinceById(id);
        }
        model.addAttribute("provinceForm", provinceForm);
        return "province/detail";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String save(Model model, @Valid ProvinceForm provinceForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            model.addAttribute("provinceForm", provinceForm);
            return "province/detail";
        }
        try{
            Province province = provinceService.saveOrUpdate(provinceForm, request);
            String successMsg;

            if(provinceForm.getId() == null){
                Object[] Args = {province.getName()};
                successMsg = messageSource.getMessage("success.create.province", Args, null);
            }else{
                Object[] Args = {province.getName()};
                successMsg = messageSource.getMessage("success.edit.province", Args, null);
            }
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
            return "redirect:/province/detail?id="+province.getId();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsg", e.getMessage());
            return "province/detail";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String name = provinceService.deleteProvince(id);
        Object[] args = {name};
        redirectAttributes.addFlashAttribute("successMsg", messageSource.getMessage("province.notif.delete", args, locale));
        return  "redirect:/province";
    }
}
