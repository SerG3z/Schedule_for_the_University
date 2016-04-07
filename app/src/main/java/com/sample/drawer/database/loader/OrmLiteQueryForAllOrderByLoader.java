package com.sample.drawer.database.loader;


import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.BaseOrmLiteLoader;
import com.j256.ormlite.dao.Dao;

/**
 * Loader for query for all with order by specified field
 */
public class OrmLiteQueryForAllOrderByLoader<T, ID> extends BaseOrmLiteLoader<T, ID> {

    private String columnNameToOrder;

    public OrmLiteQueryForAllOrderByLoader(Context context, String columnNameToOrder) {
        super(context);
        this.columnNameToOrder = columnNameToOrder;
    }

    public OrmLiteQueryForAllOrderByLoader(Context context, Dao<T, ID> dao, String columnNameToOrder) {
        super(context, dao);
        this.columnNameToOrder = columnNameToOrder;
    }

    @Override
    public List<T> loadInBackground() {
        if (dao == null) {
            throw new IllegalStateException("Dao is not initialized.");
        }
        try {
            return dao.queryBuilder().orderBy(columnNameToOrder,true).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}