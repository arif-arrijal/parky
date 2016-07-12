package edu.ejemplo.demo.presentacion.ajax;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import edu.ejemplo.demo.model.Stay;
import edu.ejemplo.demo.negocio.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ajax/stay")
public class StayAjaxController {

    @Autowired
    private StayService stayService;

    @RequestMapping("/list")
    public DatatablesResponse<Stay> list(@DatatablesParams DatatablesCriterias criterias){
        DataSet<Stay> stayDataSet = stayService.findWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(stayDataSet, criterias);
    }

}