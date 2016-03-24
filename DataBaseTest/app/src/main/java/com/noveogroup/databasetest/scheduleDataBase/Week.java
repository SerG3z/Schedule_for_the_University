package com.noveogroup.databasetest.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Weeks table
 */
@DatabaseTable
public class Week {
    @DatabaseField(id = true, canBeNull = false)
    private int number;
    @DatabaseField(canBeNull = false)
    private boolean isFirst;
    @ForeignCollectionField(eager = true)
    private ForeignCollection<Day> days;

    public Week(int number, boolean isFirst, ForeignCollection<Day> days) {
        this.number = number;
        this.isFirst = isFirst;
        this.days = days;
    }

    public Week(int number, boolean isFirst) {
        this.number = number;
        this.isFirst = isFirst;
    }

    public Week() {
    }

    public ArrayList<Day> getDays() {
        ArrayList<Day> daysList = new ArrayList<Day>();
        for (Day item : daysList) {
            daysList.add(item);
        }
        return daysList;
    }

    public void setDays(ForeignCollection<Day> days) {
        this.days = days;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public class DAO extends BaseDaoImpl<Week, Integer> {

        protected DAO(ConnectionSource connectionSource, Class<Week> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
