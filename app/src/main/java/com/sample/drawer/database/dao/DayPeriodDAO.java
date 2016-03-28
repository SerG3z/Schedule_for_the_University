package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.DayPeriod;
import com.sample.drawer.database.Period;

import java.sql.SQLException;

public class DayPeriodDAO extends BaseDaoImpl<DayPeriod, Integer> {

    public DayPeriodDAO(ConnectionSource connectionSource, Class<DayPeriod> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}