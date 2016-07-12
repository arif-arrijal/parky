package edu.ejemplo.demo.presentacion;

import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.negocio.CardService;
import edu.ejemplo.demo.presentacion.forms.CardForm;
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
@RequestMapping(value = "/card")
public class CreditCardController {

    private Logger log = LoggerFactory.getLogger(CreditCardController.class);

    @Autowired
    private CardService cardService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "card/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, @RequestParam(value = "id", required = false) Long id){
        CardForm cardForm = new CardForm();
        if (id != null){
            cardForm = cardService.findCardById(id);
        }
        model.addAttribute("cardForm", cardForm);
        return "card/detail";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String save(Model model, @Valid CardForm cardForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            model.addAttribute("cardForm", cardForm);
            return "card/detail";
        }
        try{
            TarjetaCredito card = cardService.saveOrUpdate(cardForm, request);
            String successMsg;

            if(cardForm.getId() == null){
                Object[] Args = {card.getNumeroTarjeta()};
                successMsg = messageSource.getMessage("success.create.card", Args, null);
            }else{
                Object[] Args = {card.getNumeroTarjeta()};
                successMsg = messageSource.getMessage("success.edit.card", Args, null);
            }
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
            return "redirect:/card/detail?id="+card.getId();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e.getCause());
            model.addAttribute("errorMsg", e.getMessage());
            return "card/detail";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String cardNumber = cardService.deleteCard(id);
        Object[] args = {cardNumber};
        redirectAttributes.addFlashAttribute("successMsg", messageSource.getMessage("card.notif.delete", args, locale));
        return  "redirect:/card";
    }
}
