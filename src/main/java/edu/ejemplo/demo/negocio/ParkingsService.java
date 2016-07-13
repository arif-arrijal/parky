package edu.ejemplo.demo.negocio;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.Parking;

import javax.servlet.http.HttpServletRequest;

public interface ParkingsService {

	DataSet<Parking> findWithDatatablesCriterias(DatatablesCriterias criterias);
    Parking saveOrUpdate(Parking entity, HttpServletRequest request);

}