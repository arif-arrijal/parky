package edu.ejemplo.demo.converter;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.presentacion.forms.CarListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 7/11/2016.
 */
@Component
public class CarListConverter {

    @Autowired
    private MessageSource messageSource;

    public List<CarListForm> convertForPreview(List<Coche> carList){
        List<CarListForm> previewCarList = new ArrayList<>();
        for (Coche coche : carList){
            CarListForm carListForm = new CarListForm();
            carListForm.setCarName(coche.getNombreCoche());
            carListForm.setCarPlate(coche.getMatricula());
            carListForm.setCreditCardNumber(coche.getTarjetaCredito() != null ? coche.getTarjetaCredito().getNumeroTarjeta() : messageSource.getMessage("car.label.creditcard.notregistered",null, null));
            carListForm.setHolderEmail(coche.getConductor().getPassword());
            carListForm.setActivo(coche.getActivo() == true ? "Active" : "Not Active");
            previewCarList.add(carListForm);
        }
        return previewCarList;
    }

}
