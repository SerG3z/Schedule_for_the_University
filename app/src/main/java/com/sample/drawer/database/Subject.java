package com.sample.drawer.database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Subjects table
 */
@DatabaseTable
public class Subject {
    @DatabaseField(id = true, canBeNull = false)
    private String subject;

    public Subject(String subject) {
        this.subject = subject;
    }

    public Subject() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return subject;
    }

}
