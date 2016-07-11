package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.negocio.CarService;
import edu.ejemplo.demo.presentacion.forms.CarForm;
import edu.ejemplo.demo.repositorios.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by admin on 7/8/2016.
 */
@Controller
@RequestMapping(value = "/car")
public class CarController {

    private Logger log = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private Validator validator;
    @Autowired
    private CarService carService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CarRepository carRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "car/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("carForm", new CarForm());
        return "car/add";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String submitAddCarForm(
            @ModelAttribute("carForm") CarForm carForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        String returnPath = "";
        try {
            validator.validate(carForm, bindingResult);
            if (bindingResult.hasErrors()) {
                log.info("add car validation error");
                model.addAttribute("carForm", carForm);
                returnPath = "car/add";
            } else {
                log.info("start service add car");
                Coche coche =  carService.saveOrUpdate(carForm, request);
                if(coche.getId()!= 0){
                    log.info("success create car with ID " + coche.getId() + " and car plate " + coche.getMatricula());
                    returnPath = "redirect:/car/detail/?id=" + coche.getId();
                }
                Object[] Args = {coche.getMatricula()};
                String success = messageSource.getMessage("success.create.car", Args, null);
                redirectAttributes.addFlashAttribute("successMsg", success);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMsg", ex.getMessage());
            returnPath = "/car/add";
        }
        return returnPath;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, @RequestParam(value = "id", required = false) Long id){
        CarForm carForm = new CarForm();
        if (id != null){
            carForm = carService.findCarById(id);
        }
        model.addAttribute("carForm", carForm);
        return "car/detail";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String save(Model model, @Valid CarForm carForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            model.addAttribute("parkingForm", carForm);
            return "car/detail";
        }
        try{
            Coche coche = carService.saveOrUpdate(carForm, request);
            String successMsg;

            if(carForm.getId() == null){
                Object[] Args = {coche.getMatricula()};
                successMsg = messageSource.getMessage("success.create.car", Args, null);
            }else{
                Object[] Args = {coche.getMatricula()};
                successMsg = messageSource.getMessage("success.edit.car", Args, null);
            }
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
            return "redirect:/car/detail?id="+coche.getId();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsg", e.getMessage());
            return "car/detail";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        Coche coche = carRepository.findById(id);
        String carplate = coche.getMatricula();
        carRepository.delete(coche);
        Object[] args = {carplate};
        redirectAttributes.addFlashAttribute("successMsg", messageSource.getMessage("car.notif.delete", args, locale));
        return  "redirect:/car";
    }
}