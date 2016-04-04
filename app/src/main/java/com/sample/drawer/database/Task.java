package com.sample.drawer.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Tasks table
 */
@DatabaseTable
public class Task {
    public static final String FIELD_DAY = "targetday";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String task;
    @DatabaseField(canBeNull = false)
    private boolean done;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = FIELD_DAY)
    private Day targetDay;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Subject subject;

    public Task(String task,Day targetDay, Subject subject) {
        this.task = task;
        this.subject = subject;
        this.targetDay = targetDay;
        done = false;
    }

    public Task(String task) {
        this.task = task;
        this.subject = null;
        this.targetDay = null;
        done = false;
    }

    public Task() {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Day getTargetDay() {
        return targetDay;
    }

    public void setTargetDay(Day targetDay) {
        this.targetDay = targetDay;
    }

    public int getId() {
        return id;
    }
}
