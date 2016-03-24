package com.noveogroup.databasetest.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A day representation
 */
@DatabaseTable
public class Day {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE)
    private Date date;
    @ForeignCollectionField(eager = true)
    private ForeignCollection<Period> periods;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Week week;

    public Day(Date date, ForeignCollection<Period> periods) {
        this.date = date;
        this.periods = periods;
    }

    public Day(Date date) {
        this.date = date;
    }

    public Day() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Period> getPeriods() {
        ArrayList<Period> periodsList = new ArrayList<Period>();
        for (Period item : periodsList) {
            periodsList.add(item);
        }
        return periodsList;
    }

    public void setPeriods(ForeignCollection<Period> periods) {
        this.periods = periods;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public class DAO extends BaseDaoImpl<Day, Integer> {

        protected DAO(ConnectionSource connectionSource, Class<Day> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
