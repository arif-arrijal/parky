package edu.ejemplo.demo.negocio;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.Stay;
import edu.ejemplo.demo.presentacion.forms.StayForm;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by admin on 7/11/2016.
 */
public interface StayService {

    Stay saveOrUpdate(StayForm stayForm, HttpServletRequest request) throws IOException;
    DataSet<Stay> findWithDatatablesCriterias(DatatablesCriterias criterias);
    StayForm findStayById(Long id);
    String deleteStay(Long id);
}
