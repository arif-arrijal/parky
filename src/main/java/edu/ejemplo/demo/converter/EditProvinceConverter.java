package edu.ejemplo.demo.converter;

import edu.ejemplo.demo.model.Province;
import edu.ejemplo.demo.presentacion.forms.ProvinceForm;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 7/11/2016.
 */
@Component
public class EditProvinceConverter {

    public ProvinceForm convertToEditAndDetail(Province province){
        ProvinceForm form = new ProvinceForm();
        form.setId(province.getId());
        form.setName(province.getName());
        form.setValue(province.getValue());
        return form;
    }

}
