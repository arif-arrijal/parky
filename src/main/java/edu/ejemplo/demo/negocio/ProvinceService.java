package edu.ejemplo.demo.negocio;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.Province;
import edu.ejemplo.demo.presentacion.forms.ProvinceForm;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by admin on 7/11/2016.
 */
public interface ProvinceService {

    Province saveOrUpdate(ProvinceForm provinceForm, HttpServletRequest request) throws IOException;
    DataSet<Province> findWithDatatablesCriterias(DatatablesCriterias criterias);
    ProvinceForm findProvinceById(Long id);
    String deleteProvince(Long id);
}
