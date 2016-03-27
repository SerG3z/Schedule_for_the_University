package com.sample.drawer.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.EagerForeignCollection;
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

    public static final String FIELD_DAYS_NAME = "days";

    @DatabaseField(id = true, canBeNull = false)
    private int number;
    @DatabaseField(canBeNull = false)
    private boolean isFirst;
    @DatabaseField(canBeNull = false)
    private boolean isControlPoint;
    @ForeignCollectionField(eager = true, columnName = FIELD_DAYS_NAME)
    private ForeignCollection<Day> days;

    public Week(int number, boolean isFirst,boolean isControlPoint, ForeignCollection<Day> days) {
        this.number = number;
        this.isFirst = isFirst;
        this.days = days;
        this.isControlPoint = isControlPoint;
    }

    public Week(int number, boolean isFirst, boolean isControlPoint, Week.DAO weekDAO) {
        this.number = number;
        this.isFirst = isFirst;
        this.isControlPoint = isControlPoint;
        try {
            days = weekDAO.getEmptyForeignCollection(FIELD_DAYS_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Week() {
    }

    public ArrayList<Day> getDays() {
        ArrayList<Day> daysList = new ArrayList<Day>();
        for (Day item : days) {
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

    public void addDay(Day day){
        days.add(day);
    }

    public class DAO extends BaseDaoImpl<Week, Integer> {

        protected DAO(ConnectionSource connectionSource, Class<Week> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
