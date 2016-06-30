package edu.ejemplo.demo.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zulfy on 04/04/16.
 */
@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "dashboard/index";
    }

}