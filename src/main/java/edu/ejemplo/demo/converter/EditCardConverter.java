package edu.ejemplo.demo.converter;

import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.presentacion.forms.CardForm;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Created by admin on 7/11/2016.
 */
@Component
public class EditCardConverter {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

    public CardForm convertToEditAndDetail(TarjetaCredito card){
        String expirationDate = dateFormat.format(card.getFechaCaducidad());
        CardForm form = new CardForm();
        form.setId(card.getId());
        form.setCreditCardNumber(card.getNumeroTarjeta());
        form.setNameHolder(card.getNombreTitular());
        form.setExpirationDate(expirationDate);
        form.setUserId(card.getUsuario().getId());
        return form;
    }

}
