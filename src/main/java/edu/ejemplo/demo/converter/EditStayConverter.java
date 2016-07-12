package edu.ejemplo.demo.converter;

import edu.ejemplo.demo.model.Stay;
import edu.ejemplo.demo.presentacion.forms.StayForm;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 7/11/2016.
 */
@Component
public class EditStayConverter {

    public StayForm convertToEditAndDetail(Stay stay){
        StayForm form = new StayForm();
        form.setId(stay.getId());
        form.setName(stay.getName());
        form.setFeePerMinutes(stay.getFeePerMinutes());
        return form;
    }

}
