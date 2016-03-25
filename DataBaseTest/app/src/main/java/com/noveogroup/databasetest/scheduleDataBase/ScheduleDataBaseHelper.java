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
        if (daos[dao.ordinal()] == null)
        {
            try {
                switch (dao) {
                    case CLASSROOM:
                        daos[DaoType.CLASSROOM.ordinal()] = (new Classroom()).new DAO(getConnectionSource(), Classroom.class);
                        break;
                    case TEACHER:
                        daos[DaoType.TEACHER.ordinal()] = (new Teacher()).new DAO(getConnectionSource(), Teacher.class);
                        break;
                    case PERIOD:
                        daos[DaoType.PERIOD.ordinal()] =  (new Period()).new DAO(getConnectionSource(), Period.class);
                        break;
                    case DAY:
                        daos[DaoType.DAY.ordinal()] = (new Day()).new DAO(getConnectionSource(), Day.class);
                        break;
                    case PERIOD_TIME:
                        daos[DaoType.PERIOD_TIME.ordinal()] = (new PeriodTime()).new DAO(getConnectionSource(), PeriodTime.class);
                        break;
                    case PERIOD_TYPE:
                        daos[DaoType.PERIOD_TYPE.ordinal()] = (new PeriodType()).new DAO(getConnectionSource(), PeriodType.class);
                        break;
                    case SUBJECT:
                        daos[DaoType.SUBJECT.ordinal()] = (new Subject()).new DAO(getConnectionSource(), Subject.class);
                        break;
                    case TASK:
                        daos[DaoType.TASK.ordinal()] = (new Task()).new DAO(getConnectionSource(), Task.class);
                        break;
                    case WEEK:
                        daos[DaoType.WEEK.ordinal()] = (new Week()).new DAO(getConnectionSource(), Week.class);
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return daos[dao.ordinal()];
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