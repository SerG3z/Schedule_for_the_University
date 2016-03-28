package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Teacher;

import java.sql.SQLException;

public class TeacherDAO extends BaseDaoImpl<Teacher, String> {

    public TeacherDAO(ConnectionSource connectionSource, Class<Teacher> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}