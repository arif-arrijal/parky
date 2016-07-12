package edu.ejemplo.demo.negocio;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.presentacion.forms.CardForm;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 7/11/2016.
 */
public interface CardService {

    TarjetaCredito saveOrUpdate(CardForm form, HttpServletRequest request) throws IOException;
    DataSet<TarjetaCredito> findWithDatatablesCriterias(DatatablesCriterias criterias);
    CardForm findCardById(Long id);
    String deleteCard(Long id);
    List<CardForm> findAll();
}
