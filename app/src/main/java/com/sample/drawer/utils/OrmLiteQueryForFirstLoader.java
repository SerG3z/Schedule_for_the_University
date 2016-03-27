package com.sample.drawer.utils;

import android.content.Context;

import com.j256.ormlite.android.apptools.BaseOrmLiteLoader;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A loader to get elements by id
 */
public class OrmLiteQueryForFirstLoader<T, ID> extends BaseOrmLiteLoader<T, ID> {

    private PreparedQuery<T> query;

    public OrmLiteQueryForFirstLoader(Context context) {
        super(context);
    }

    public OrmLiteQueryForFirstLoader(Context context, Dao<T, ID> dao, PreparedQuery<T> query) {
        super(context, dao);
        this.query = query;
    }

    @Override
    public List<T> loadInBackground() {
        if (dao == null) {
            throw new IllegalStateException("Dao is not initialized.");
        }
        if (query == null) {
            throw new IllegalStateException("PreparedQuery is not initialized.");
        }
        try {
            List<T> list = new ArrayList<>();
            list.add(dao.queryForFirst(query));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
