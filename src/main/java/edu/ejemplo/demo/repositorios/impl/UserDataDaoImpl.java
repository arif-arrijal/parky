package edu.ejemplo.demo.repositorios.impl;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.repositorios.DataDaoUtils;
import edu.ejemplo.demo.repositorios.UserDataDao;
import edu.ejemplo.demo.repositorios.UserDataDaoUtils;
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
public class UserDataDaoImpl implements UserDataDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findWithDatatablesCriterias(DatatablesCriterias criterias) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM User t ");

        /**
         * Step 1: global and individual column filtering
         */
        queryBuilder.append(UserDataDaoUtils.getFilterQuery(criterias));

        /**
         * Step 2: sorting
         */
        DataDaoUtils.sortingData(criterias, queryBuilder);
        TypedQuery<User> query = entityManager.createQuery(
                queryBuilder.toString(), User.class);

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
        Query query = entityManager.createQuery("SELECT COUNT(t) FROM User t WHERE t.active = true");
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getFilteredCount(DatatablesCriterias criterias) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(t) FROM User t  ");
        queryBuilder.append(UserDataDaoUtils.getFilterQuery(criterias));
        Query query = entityManager.createQuery(queryBuilder.toString());
        return (Long) query.getSingleResult();
    }
}
