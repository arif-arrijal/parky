package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.presentacion.forms.CardForm;
import edu.ejemplo.demo.repositorios.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by admin on 7/9/2016.
 */
@Component
public class CardValidator {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private TarjetaRepository cardRepository;


    public void validateCard(CardForm form) throws IOException {
        String creditCardNumber = form.getCreditCardNumber().trim();

        if (form.getId() == null){
            if (cardRepository.countByNumeroTarjeta(creditCardNumber) > 0) {
                Object[] errorArgs = {creditCardNumber};
                String error = messageSource.getMessage("error.card.number.already.exist", errorArgs, null);
                throw new BusinessLogicException(error);
            }
        }

    }
}
