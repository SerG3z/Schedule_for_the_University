package com.sample.drawer.database;

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

    public static final String FIELD_TEACHER = "teacher_name";

    @DatabaseField(id = true, canBeNull = false, columnName = FIELD_TEACHER)
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

    @Override
    public String toString() {
        return teacher;
    }

}
