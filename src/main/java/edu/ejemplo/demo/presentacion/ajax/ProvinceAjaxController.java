package edu.ejemplo.demo.presentacion.ajax;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import edu.ejemplo.demo.model.Province;
import edu.ejemplo.demo.negocio.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zulfy on 30/06/16.
 */
@RestController
@RequestMapping("/ajax/province")
public class ProvinceAjaxController {

    @Autowired
    private ProvinceService provinceService;

    @RequestMapping("/list")
    public DatatablesResponse<Province> list(@DatatablesParams DatatablesCriterias criterias){
        DataSet<Province> provinceDataSet = provinceService.findWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(provinceDataSet, criterias);
    }

}