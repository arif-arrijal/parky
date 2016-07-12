package edu.ejemplo.demo.presentacion.ajax;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.negocio.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zulfy on 30/06/16.
 */
@RestController
@RequestMapping("/ajax/card")
public class CardAjaxController {

    @Autowired
    private CardService cardService;

    @RequestMapping("/list")
    public DatatablesResponse<TarjetaCredito> list(@DatatablesParams DatatablesCriterias criterias){
        DataSet<TarjetaCredito> cardDataSet = cardService.findWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(cardDataSet, criterias);
    }

}