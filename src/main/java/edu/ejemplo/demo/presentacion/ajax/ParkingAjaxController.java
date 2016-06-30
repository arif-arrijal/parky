package edu.ejemplo.demo.presentacion.ajax;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.negocio.ParkingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zulfy on 30/06/16.
 */
@RestController
@RequestMapping("/ajax/parking")
public class ParkingAjaxController {

    @Autowired
    private ParkingsService parkingsService;

    @RequestMapping("/list")
    public DatatablesResponse<Parking> list(@DatatablesParams DatatablesCriterias criterias){
        DataSet<Parking> areaDataSet = parkingsService.findWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(areaDataSet, criterias);
    }

}