package com.sample.drawer.database;

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
public class PeriodTime implements Comparable<PeriodTime>{

    public static final String FIELD_BEGIN_TIME = "begin_time";

    @DatabaseField(generatedId = true)
    private int number;
    //format: "%2d:%2d"
    @DatabaseField(canBeNull = false, columnName = FIELD_BEGIN_TIME)
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

    @Override
    public String toString() {
        return beginTime + "\n" + endTime;
    }


    @Override
    public int compareTo(PeriodTime another) {
        int result = beginTime.compareTo(another.getBeginTime());
        if (result == 0){
            return endTime.compareTo(another.getEndTime());
        }
        return result;
    }
}
