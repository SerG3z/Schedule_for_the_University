package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Period;

import java.sql.SQLException;

public class PeriodDAO extends BaseDaoImpl<Period, Integer> {

    public PeriodDAO(ConnectionSource connectionSource, Class<Period> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}