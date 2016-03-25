package com.noveogroup.databasetest.scheduleDataBase;

import android.content.Context;

import com.j256.ormlite.android.apptools.BaseOrmLiteLoader;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A loader to get elements by id
 */
public class OrmLiteQueryForIdLoader<T, ID> extends BaseOrmLiteLoader<T, ID> {

    private ID requestedId;

    public OrmLiteQueryForIdLoader(Context context) {
        super(context);
    }

    public OrmLiteQueryForIdLoader(Context context, Dao<T, ID> dao, ID id) {
        super(context, dao);
        requestedId = id;
    }

    public ID getRequestedId() {
        return requestedId;
    }

    public void setRequestedId(ID requestedId) {
        this.requestedId = requestedId;
    }

    @Override
    public List<T> loadInBackground() {
        if (dao == null) {
            throw new IllegalStateException("Dao is not initialized.");
        }
        if (requestedId == null) {
            throw new IllegalStateException("RequestedId is not initialized.");
        }
        try {
            List<T> list = new ArrayList<>();
            list.add(dao.queryForId(requestedId));
            return list;
        } catch (SQLException e) {
            // XXX: is this really the right thing to do? Maybe throw RuntimeException?
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
