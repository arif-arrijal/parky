package edu.ejemplo.demo.repositorios;

import com.github.dandelion.core.util.StringUtils;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zulfy on 21/01/15.
 */
public class DataDaoUtils {

    public static StringBuilder getFilterQuery(DatatablesCriterias criterias){
        StringBuilder queryBuilder = new StringBuilder();
        List<String> paramList = new ArrayList<String>();

        /**
         * Step 1.1: global filtering
         */
        if (StringUtils.isNotBlank(criterias.getSearch()) && criterias.hasOneSearchableColumn()) {
            queryBuilder.append(" WHERE ");

            for (ColumnDef columnDef : criterias.getColumnDefs()) {
                if (columnDef.isSearchable() && StringUtils.isBlank(columnDef.getSearch())) {
                    paramList.add(" LOWER(t." + columnDef.getName()
                            + ") LIKE '%?%'".replace("?", criterias.getSearch().toLowerCase()));
                }
            }

            Iterator<String> itr = paramList.iterator();
            if(paramList.size() > 0) {
                queryBuilder.append("(");
            }
            while (itr.hasNext()) {
                queryBuilder.append(itr.next());
                if (itr.hasNext()) {
                    queryBuilder.append(" OR ");
                }
            }
            if(paramList.size() > 0){
                queryBuilder.append(")");
            }
        }

        /**
         * Step 1.2: individual column filtering
         */
        if (criterias.hasOneSearchableColumn() && criterias.hasOneFilteredColumn()) {
            paramList = new ArrayList<String>();

            if(!queryBuilder.toString().contains("WHERE")){
                queryBuilder.append(" WHERE ");
            }
            else{
                queryBuilder.append(" AND ");
            }

            for (ColumnDef columnDef : criterias.getColumnDefs()) {
                if (columnDef.isSearchable()){

                    if(StringUtils.isNotBlank(columnDef.getSearch())) {
                        paramList.add(" LOWER(t." + columnDef.getName()
                                + ") LIKE '%?%'".replace("?", columnDef.getSearch().toLowerCase()));
                    }
                }
            }

            Iterator<String> itr = paramList.iterator();
            if(paramList.size() > 0) {
                queryBuilder.append("(");
            }
            while (itr.hasNext()) {
                queryBuilder.append(itr.next());
                if (itr.hasNext()) {
                    queryBuilder.append(" AND ");
                }
            }
            if(paramList.size() > 0){
                queryBuilder.append(")");
            }
        }

        return queryBuilder;
    }

    public static void sortingData(DatatablesCriterias criterias, StringBuilder queryBuilder){
        if (criterias.hasOneSortedColumn()) {
            List<String> orderParams = new ArrayList<String>();
            queryBuilder.append(" ORDER BY ");
            for (ColumnDef columnDef : criterias.getSortedColumnDefs()) {
                orderParams.add("t." + columnDef.getName() + " " + columnDef.getSortDirection());
            }

            Iterator<String> itr2 = orderParams.iterator();
            while (itr2.hasNext()) {
                queryBuilder.append(itr2.next());
                if (itr2.hasNext()) {
                    queryBuilder.append(" , ");
                }
            }
        }
    }

}
