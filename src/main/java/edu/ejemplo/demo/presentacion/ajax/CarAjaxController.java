package edu.ejemplo.demo.presentacion.ajax;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.negocio.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zulfy on 30/06/16.
 */
@RestController
@RequestMapping("/ajax/car")
public class CarAjaxController {

    @Autowired
    private CarService carService;

    @RequestMapping("/list")
    public DatatablesResponse<Coche> list(@DatatablesParams DatatablesCriterias criterias){
        DataSet<Coche> carDataSet = carService.findWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(carDataSet, criterias);
    }

}