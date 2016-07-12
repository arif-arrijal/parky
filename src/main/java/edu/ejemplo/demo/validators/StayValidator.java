package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.presentacion.forms.StayForm;
import edu.ejemplo.demo.repositorios.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by admin on 7/9/2016.
 */
@Component
public class StayValidator {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private StayRepository stayRepository;


    public void validateStay(StayForm form) throws IOException {
        String name = form.getName().trim();

        if (form.getId() == null){
            if (stayRepository.countByName(name) > 0) {
                Object[] errorArgs = {name};
                String error = messageSource.getMessage("error.stay.name.already.exist", errorArgs, null);
                throw new BusinessLogicException(error);
            }
        }

    }
}
