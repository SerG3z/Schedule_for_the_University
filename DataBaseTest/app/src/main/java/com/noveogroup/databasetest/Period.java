package com.noveogroup.databasetest;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Periods table
 */
@DatabaseTable
public class Period {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String subject;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private Teacher teacher;

    @DatabaseField(canBeNull = false)
    private String time;

    @DatabaseField
    private String task;

    public Period(String subject, Teacher teacher, String time, String task) {
        this.subject = subject;
        this.teacher = teacher;
        this.time = time;
        this.task = task;
    }

    public Period() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "id: " + id + "\nSubject: " + subject + "\nTeacher: " + teacher + "\nTime: " + time + "\nTask: " + task;
    }
}
