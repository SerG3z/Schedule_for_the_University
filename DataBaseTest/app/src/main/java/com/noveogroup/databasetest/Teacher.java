package com.noveogroup.databasetest;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Teachers table
 */
@DatabaseTable
public class Teacher {

    public static final String TEACHER_FIELD_NAME = "teacher";

    @DatabaseField(generatedId = true)
    private int id;

    public String getTeacherName() {
        return teacher;
    }

    public void getTeacherName(String teacher) {
        this.teacher = teacher;
    }

    @DatabaseField(canBeNull = false, columnName = TEACHER_FIELD_NAME)
    private String teacher;

    public Teacher(String teacher) {
        this.teacher = teacher;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Teacher: " + teacher;
    }
}
