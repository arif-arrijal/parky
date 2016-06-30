package edu.ejemplo.demo.repositorios;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import java.util.List;

/**
 * Created by zulfy on 29/06/16.
 */
public interface BaseDataDao<T> {

    List<T> findWithDatatablesCriterias(DatatablesCriterias criterias);

    Long getTotalCount();

    Long getFilteredCount(DatatablesCriterias criterias);

}