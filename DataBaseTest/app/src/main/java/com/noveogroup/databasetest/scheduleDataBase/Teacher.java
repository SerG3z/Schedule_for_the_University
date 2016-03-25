package com.noveogroup.databasetest.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Teachers table
 */
@DatabaseTable
public class Teacher {
    @DatabaseField(id = true, canBeNull = false)
    private String teacher;

    public Teacher(String teacher) {
        this.teacher = teacher;
    }

    public Teacher() {
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public class DAO extends BaseDaoImpl<Teacher, String> {

        protected DAO(ConnectionSource connectionSource, Class<Teacher> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
