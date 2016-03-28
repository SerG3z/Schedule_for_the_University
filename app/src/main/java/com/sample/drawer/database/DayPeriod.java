package com.sample.drawer.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Day - period relation
 */
@DatabaseTable
public class DayPeriod {
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    Day day;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    Period period;

    public DayPeriod(Day day, Period period) {
        this.day = day;
        this.period = period;
    }

    public DayPeriod(){
    }
}
