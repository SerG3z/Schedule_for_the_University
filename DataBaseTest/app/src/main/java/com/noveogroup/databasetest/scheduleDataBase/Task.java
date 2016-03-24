package com.noveogroup.databasetest.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Tasks table
 */
@DatabaseTable
public class Task {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String task;
    @DatabaseField(canBeNull = false)
    private boolean done;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Period targetPeriod;

    public Task(String task, Period targetPeriod) {
        this.task = task;
        this.targetPeriod = targetPeriod;
        done = false;
    }

    public Task(String task) {
        this.task = task;
        this.targetPeriod = null;
        done = false;
    }

    public Task() {
    }

    public Period getTargetPeriod() {
        return targetPeriod;
    }

    public void setTargetPeriod(Period targetPeriod) {
        this.targetPeriod = targetPeriod;
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

    public class DAO extends BaseDaoImpl<Task, Integer> {

        protected DAO(ConnectionSource connectionSource, Class<Task> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
