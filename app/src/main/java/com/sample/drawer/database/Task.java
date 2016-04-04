package com.sample.drawer.database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.sample.drawer.database.dao.DayDAO;
import com.sample.drawer.database.dao.PeriodDAO;

import java.sql.SQLException;

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
    private Period targetPeriod;

    public Task(String task,Day targetDay, Period targetPeriod) {
        this.task = task;
        this.targetPeriod = targetPeriod;
        this.targetDay = targetDay;
        done = false;
    }

    public Task(String task) {
        this.task = task;
        this.targetPeriod = null;
        this.targetDay = null;
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
