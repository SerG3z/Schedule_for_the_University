package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Week;

import java.sql.SQLException;

public class WeekDAO extends BaseDaoImpl<Week, Integer> {

    public WeekDAO(ConnectionSource connectionSource, Class<Week> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}