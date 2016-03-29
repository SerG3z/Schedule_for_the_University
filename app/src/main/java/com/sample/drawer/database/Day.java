package com.sample.drawer.database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.sample.drawer.database.dao.DayDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A day representation
 */
@DatabaseTable
public class Day {

    public static final String FIELD_DATE_NAME = "date";
    public static final String FIELD_DAY_OF_WEEK = "day_of_week";
    public static final String FIELD_ID_NAME = "id";

    @DatabaseField(generatedId = true, columnName = FIELD_ID_NAME)
    private int id;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE, columnName = FIELD_DATE_NAME)
    private Date date;
    @DatabaseField(canBeNull = false, columnName = FIELD_DAY_OF_WEEK)
    private int dayOfWeek;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Week week;
    public Day(Date date, ForeignCollection<Period> periods) {
        this.date = date;
    }
    public Day(Date date, int dayOfWeek) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
    }

    public Day() {
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public int getId() {
        return id;
    }
}
