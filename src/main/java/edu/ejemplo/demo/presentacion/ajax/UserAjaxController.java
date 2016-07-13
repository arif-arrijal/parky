package edu.ejemplo.demo.presentacion.ajax;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zulfy on 30/06/16.
 */
@RestController
@RequestMapping("/ajax/user")
public class UserAjaxController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public DatatablesResponse<User> list(@DatatablesParams DatatablesCriterias criterias){
        DataSet<User> userDataSet = userService.findWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(userDataSet, criterias);
    }

}