package edu.ejemplo.demo.negocio;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.User;

import javax.servlet.http.HttpServletRequest;

public interface ParkingsService {

	DataSet<Parking> findWithDatatablesCriterias(DatatablesCriterias criterias);
	Iterable<Parking> getParkings();
    Parking saveOrUpdate(Parking entity, HttpServletRequest request);
	static void registrar(User user, Parking parking) throws YaExisteException{

	}
}