package com.noveogroup.databasetest;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Teacher data access object
 */
public class TeacherDAO extends BaseDaoImpl<Teacher, String> {

    protected TeacherDAO(ConnectionSource connectionSource,
                         Class<Teacher> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Teacher> getAllTeachers() throws SQLException {
        return this.queryForAll();
    }

    public PreparedQuery<Teacher> getTeacherByNameQuery(String name) throws SQLException {
        QueryBuilder<Teacher, String> queryBuilder = queryBuilder();
        queryBuilder.where().eq(Teacher.TEACHER_FIELD_NAME, name);
        PreparedQuery<Teacher> preparedQuery = queryBuilder.prepare();
        return preparedQuery;
    }

}
