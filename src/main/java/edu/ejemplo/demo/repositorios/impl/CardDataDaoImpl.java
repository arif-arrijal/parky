package edu.ejemplo.demo.repositorios.impl;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.repositorios.CardDataDao;
import edu.ejemplo.demo.repositorios.DataDaoUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by zulfy on 30/06/16.
 */
@Repository
public class CardDataDaoImpl implements CardDataDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TarjetaCredito> findWithDatatablesCriterias(DatatablesCriterias criterias) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM TarjetaCredito t");

        /**
         * Step 1: global and individual column filtering
         */
        queryBuilder.append(DataDaoUtils.getFilterQuery(criterias));

        /**
         * Step 2: sorting
         */
        DataDaoUtils.sortingData(criterias, queryBuilder);
        TypedQuery<TarjetaCredito> query = entityManager.createQuery(
                queryBuilder.toString(), TarjetaCredito.class);

        int i= 1;

        /**
         * Step 3: paging
         */
        query.setFirstResult(criterias.getStart());
        query.setMaxResults(criterias.getLength());
        return query.getResultList();
    }

    @Override
    public Long getTotalCount() {
        Query query = entityManager.createQuery("SELECT COUNT(t) FROM TarjetaCredito t");
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getFilteredCount(DatatablesCriterias criterias) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(t) FROM TarjetaCredito t");
        queryBuilder.append(DataDaoUtils.getFilterQuery(criterias));
        Query query = entityManager.createQuery(queryBuilder.toString());
        return (Long) query.getSingleResult();
    }
}
