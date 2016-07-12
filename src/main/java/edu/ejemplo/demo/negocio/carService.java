package edu.ejemplo.demo.negocio;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.presentacion.forms.CarForm;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by admin on 7/8/2016.
 */
public interface CarService {

    Coche saveOrUpdate(CarForm carForm, HttpServletRequest request) throws IOException;
    DataSet<Coche> findWithDatatablesCriterias(DatatablesCriterias criterias);
    CarForm findCarById(Long id);
    byte[] getCarPhoto(Long id);
    String deleteCar(Long id);

}
