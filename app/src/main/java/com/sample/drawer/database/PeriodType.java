package com.sample.drawer.database;

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

    public static final String FIELD_PERIOD_TYPE = "period_type";

    @DatabaseField(id = true, canBeNull = false, columnName = FIELD_PERIOD_TYPE)
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

}
