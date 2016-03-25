package com.sample.drawer.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Classroom table
 */
@DatabaseTable
public class Classroom {
    @DatabaseField(id = true, canBeNull = false)
    private String classroom;

    public Classroom(String classroom) {
        this.classroom = classroom;
    }

    public Classroom() {
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public class DAO extends BaseDaoImpl<Classroom, String> {

        protected DAO(ConnectionSource connectionSource, Class<Classroom> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
