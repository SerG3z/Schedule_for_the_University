package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.Period;
import com.sample.drawer.database.Task;

import java.sql.SQLException;

public class TaskDAO extends BaseDaoImpl<Task, Integer> {

    public TaskDAO(ConnectionSource connectionSource, Class<Task> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    //TODO: переделать запросы, с учетом DayPeriod
    public PreparedQuery<Task> getTasksOrderedByDate(PeriodDAO periodDao, DayDAO dayDao) throws SQLException {
        QueryBuilder<Day, Integer> dayQb = dayDao.queryBuilder();
        dayQb.orderBy(Day.FIELD_DATE_NAME, true);

        QueryBuilder<Period, Integer> periodQb = periodDao.queryBuilder();
        periodQb.join(dayQb);

        QueryBuilder<Task, Integer> taskQb = queryBuilder();
        return taskQb.join(periodQb).prepare();
    }

    public PreparedQuery<Task> getTasksOrderedBySubject(PeriodDAO periodDao) throws SQLException {
        QueryBuilder<Period, Integer> periodQb = periodDao.queryBuilder();
        periodQb.orderBy(Period.FIELD_SUBJECT, true);
        QueryBuilder<Task, Integer> taskQb = queryBuilder();
        return taskQb.join(periodQb).prepare();
    }
}