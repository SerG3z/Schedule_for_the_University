package com.sample.drawer.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Period type table (lecture/practice/etc.)
 */
@DatabaseTable
public class PeriodType {
    @DatabaseField(id = true, canBeNull = false)
    private String periodType;

    public PeriodType(String periodType) {
        this.periodType = periodType;
    }

    public PeriodType() {
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    @Override
    public String toString() {
        return periodType;
    }

    public class DAO extends BaseDaoImpl<PeriodType, String> {

        protected DAO(ConnectionSource connectionSource, Class<PeriodType> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}
