package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.PeriodTime;

import java.sql.SQLException;

public class PeriodTimeDAO extends BaseDaoImpl<PeriodTime, Integer> {

    public PeriodTimeDAO(ConnectionSource connectionSource, Class<PeriodTime> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
