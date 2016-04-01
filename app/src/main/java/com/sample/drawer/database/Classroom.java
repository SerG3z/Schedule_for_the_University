package com.sample.drawer.database;

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

    public static final String FIELD_CLASSROOM = "classroom_num";

    @DatabaseField(id = true, canBeNull = false, columnName = FIELD_CLASSROOM)
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


    @Override
    public String toString() {
        return classroom;
    }

}
