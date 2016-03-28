package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.PeriodType;

import java.sql.SQLException;


public class PeriodTypeDAO extends BaseDaoImpl<PeriodType, String> {

    public PeriodTypeDAO(ConnectionSource connectionSource, Class<PeriodType> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}