package com.sample.drawer.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Day - period relation
 */
@DatabaseTable
public class DayPeriod {

    public static final String FIELD_DAY = "day";
    public static final String FIELD_PERIOD = "period";

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true,
            columnName = FIELD_DAY)
    Day day;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true,
            columnName = FIELD_PERIOD)
    Period period;


    public DayPeriod(Day day, Period period) {
        this.day = day;
        this.period = period;
    }

    public DayPeriod(){
    }


}
