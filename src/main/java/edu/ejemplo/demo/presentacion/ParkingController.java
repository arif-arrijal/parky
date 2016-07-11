package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.negocio.ParkingsService;
import edu.ejemplo.demo.presentacion.forms.ParkingForm;
import edu.ejemplo.demo.repositorios.ParkingRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by zulfy on 30/06/16.
 */
@Controller
@RequestMapping(value = "/parking")
public class ParkingController {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ParkingsService parkingsService;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(passwordValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "parking/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, @RequestParam(value = "id", required = false) Parking parking){
        ParkingForm parkingForm = new ParkingForm();
        if(parking != null){
            BeanUtils.copyProperties(parking, parkingForm);
        }
        model.addAttribute("parkingForm", parkingForm);
        return "parking/detail";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String save(Model model, @Valid ParkingForm parkingForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            model.addAttribute("parkingForm", parkingForm);
            return "parking/detail";
        }
        try{
            Parking parking = new Parking();
            BeanUtils.copyProperties(parkingForm, parking);
            parkingsService.saveOrUpdate(parking, request);
            String successMsg;
            if(parkingForm.getId() == null){
                successMsg = messageSource.getMessage("parking.notif.create.success", new Object[]{parking.getNombre()}, locale);
            }else{
                successMsg = messageSource.getMessage("global.notif.success.edit", new Object[]{parking.getNombre()}, locale);
            }
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
            return "redirect:/parking";
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsg", e.getMessage());
            return "parking/detail";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") Parking parking, Model model, Locale locale) {
        parkingRepository.delete(parking.getId());
        model.addAttribute("successMsg", messageSource.getMessage("global.notif.success.delete", new Object[]{parking.getNombre()}, locale));
        return "parking";
    }
}