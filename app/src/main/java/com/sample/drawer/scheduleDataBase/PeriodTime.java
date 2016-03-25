package com.sample.drawer.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.sample.drawer.utils.TimeHelper;

import java.sql.SQLException;

/**
 * Period time table
 */
@DatabaseTable
public class PeriodTime {

    @DatabaseField(generatedId = true)
    private int number;
    //format: "%2d:%2d"
    @DatabaseField(canBeNull = false)
    private String beginTime;
    //format: "%2d:%2d"
    @DatabaseField(canBeNull = false)
    private String endTime;

    public PeriodTime(String beginTime, String endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public PeriodTime() {
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDuration() {
        return TimeHelper.getDeltaTimeMinutes(beginTime, endTime);
    }

    public class DAO extends BaseDaoImpl<PeriodTime, Integer> {

        protected DAO(ConnectionSource connectionSource, Class<PeriodTime> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
