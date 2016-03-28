package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Day;

import java.sql.SQLException;
import java.util.Date;

public class DayDAO extends BaseDaoImpl<Day, Integer> {

    public DayDAO(ConnectionSource connectionSource, Class<Day> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PreparedQuery<Day> getDayByDate(Date date) throws SQLException {
        QueryBuilder<Day, Integer> dayQb = queryBuilder();
        dayQb.where().eq(Day.FIELD_DATE_NAME, date);
        return dayQb.prepare();
    }

    public PreparedQuery<Day> getDayByDayOfWeek(int dayOfWeek) throws SQLException {
        QueryBuilder<Day, Integer> dayQb = queryBuilder();
        dayQb.where().eq(Day.FIELD_DAY_OF_WEEK, dayOfWeek);
        return dayQb.prepare();
    }
}
