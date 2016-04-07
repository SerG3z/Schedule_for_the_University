package com.sample.drawer.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Subject;

import java.sql.SQLException;

public class SubjectDAO extends BaseDaoImpl<Subject, String> {

    public SubjectDAO(ConnectionSource connectionSource, Class<Subject> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}