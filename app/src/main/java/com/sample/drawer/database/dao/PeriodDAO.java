package com.sample.drawer.database.dao;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.DayPeriod;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.Period;

import java.sql.SQLException;

public class PeriodDAO extends BaseDaoImpl<Period, Integer> {

    public PeriodDAO(ConnectionSource connectionSource, Class<Period> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PreparedQuery<Period> getPeriodsByDayOfWeek(int dayOfWeek) throws SQLException {
        DayDAO dayDAO = HelperFactory.getHelper().getDayDAO();
        QueryBuilder<Day, Integer> dayQb = dayDAO.queryBuilder();
        dayQb.where().eq(Day.FIELD_DAY_OF_WEEK, dayOfWeek);

        DayPeriodDAO dayPeriodDAO = HelperFactory.getHelper().getDayPeriodDAO();
        QueryBuilder<DayPeriod, Integer> dayPeriodQB = dayPeriodDAO.queryBuilder();
        dayPeriodQB = dayPeriodQB.join(dayQb);
        QueryBuilder<Period, Integer> periodQb = queryBuilder();
        periodQb = periodQb.join(dayPeriodQB);
        periodQb.distinct().orderBy(Period.FIELD_TIME, true);
        return periodQb.prepare();
    }

    public PreparedQuery<Period> getPeriodsByDay(int dayID) throws SQLException {
        DayDAO dayDAO = HelperFactory.getHelper().getDayDAO();
        QueryBuilder<Day, Integer> dayQb = dayDAO.queryBuilder();
        dayQb.where().eq(Day.FIELD_ID_NAME, dayID);

        DayPeriodDAO dayPeriodDAO = HelperFactory.getHelper().getDayPeriodDAO();
        QueryBuilder<DayPeriod, Integer> dayPeriodQB = dayPeriodDAO.queryBuilder();
        dayPeriodQB = dayPeriodQB.join(dayQb);

        QueryBuilder<Period, Integer> periodQb = queryBuilder();
        periodQb = periodQb.join(dayPeriodQB);

        periodQb.distinct().orderBy(Period.FIELD_TIME, true);
        return periodQb.prepare();
    }
}