package com.noveogroup.databasetest.scheduleDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Schedule data base open helper
 */
public class ScheduleDataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "schedule";
    private static final int DATABASE_VERSION = 1;
    private BaseDaoImpl[] daos = {null, null, null, null, null, null, null, null, null};

    ;

    public ScheduleDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Period.class);
            TableUtils.createTable(connectionSource, Teacher.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Period.class, false);
            TableUtils.dropTable(connectionSource, Teacher.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BaseDaoImpl getDao(DaoType dao) {
        try {
            switch (dao) {
                case CLASSROOM:
                    return (new Classroom()).new DAO(getConnectionSource(), Classroom.class);
                case TEACHER:
                    return (new Teacher()).new DAO(getConnectionSource(), Teacher.class);
                case PERIOD:
                    return (new Period()).new DAO(getConnectionSource(), Period.class);
                case DAY:
                    return (new Day()).new DAO(getConnectionSource(), Day.class);
                case PERIOD_TIME:
                    return (new PeriodTime()).new DAO(getConnectionSource(), PeriodTime.class);
                case PERIOD_TYPE:
                    return (new PeriodType()).new DAO(getConnectionSource(), PeriodType.class);
                case SUBJECT:
                    return (new Subject()).new DAO(getConnectionSource(), Subject.class);
                case TASK:
                    return (new Task()).new DAO(getConnectionSource(), Task.class);
                case WEEK:
                    return (new Week()).new DAO(getConnectionSource(), Week.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        super.close();
        for (int i = 0; i < daos.length; i++) {
            daos[i] = null;
        }
    }

    public enum DaoType {CLASSROOM, TEACHER, PERIOD, DAY, PERIOD_TIME, PERIOD_TYPE, SUBJECT, TASK, WEEK}

}