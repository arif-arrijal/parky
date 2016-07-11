package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.presentacion.forms.ProvinceForm;
import edu.ejemplo.demo.repositorios.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by admin on 7/9/2016.
 */
@Component
public class ProvinceValidator {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProvinceRepository provinceRepository;


    public void validateProvince(ProvinceForm form) throws IOException {
        String name = form.getName().trim();
        Integer value = form.getValue();

        if (form.getId() == null){
            if (provinceRepository.countByName(name) > 0) {
                Object[] errorArgs = {name};
                String error = messageSource.getMessage("error.province.name.already.exist", errorArgs, null);
                throw new BusinessLogicException(error);
            }
            if (provinceRepository.countByValue(value) > 0) {
                Object[] errorArgs = {value};
                String error = messageSource.getMessage("error.province.value.already.exist", errorArgs, null);
                throw new BusinessLogicException(error);
            }
        }

    }
}
