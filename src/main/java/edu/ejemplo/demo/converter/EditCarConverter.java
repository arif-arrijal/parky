package edu.ejemplo.demo.converter;

import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.presentacion.forms.CarForm;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 7/11/2016.
 */
@Component
public class EditCarConverter {

    public CarForm convertToEditAndDetail(Coche coche){
        CarForm carForm = new CarForm();
        carForm.setId(coche.getId());
        carForm.setCarPlate(coche.getMatricula());
        carForm.setCarPlate2(coche.getMatricula());
        carForm.setCarName(coche.getNombreCoche());
        if (coche.getTarjetaCredito() != null){
            carForm.setCreditCardId(coche.getTarjetaCredito().getId());
        }

        return carForm;
    }

}
