package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Classroom;

import java.sql.SQLException;

public class ClassroomDAO extends BaseDaoImpl<Classroom, String> {

    public ClassroomDAO(ConnectionSource connectionSource, Class<Classroom> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
