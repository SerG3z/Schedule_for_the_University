package com.sample.drawer.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.ColumnArg;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.DayPeriod;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.Period;

import java.sql.SQLException;

public class DayPeriodDAO extends BaseDaoImpl<DayPeriod, Integer> {

    public DayPeriodDAO(ConnectionSource connectionSource, Class<DayPeriod> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void deleteByPeriod(Period period) throws SQLException {
        DeleteBuilder<DayPeriod, Integer> deleteBuilder = deleteBuilder();
        deleteBuilder.where().eq(DayPeriod.FIELD_PERIOD,period);
        deleteBuilder.delete();
    }
    public PreparedQuery<DayPeriod> getWithPeriodId(int periodID) throws SQLException {
        QueryBuilder<Period,Integer> periodQb = HelperFactory.getHelper()
                .getPeriodDAO().queryBuilder();
        periodQb.where().idEq(periodID);

        QueryBuilder<DayPeriod,Integer> queryBuilder = queryBuilder();
        return queryBuilder.join(periodQb).prepare();
    }
}