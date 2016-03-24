package com.noveogroup.databasetest;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Periods data access object
 */
public class PeriodDAO extends BaseDaoImpl<Period, Integer> {

    public PeriodDAO(ConnectionSource connectionSource,
                        Class<Period> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

}
