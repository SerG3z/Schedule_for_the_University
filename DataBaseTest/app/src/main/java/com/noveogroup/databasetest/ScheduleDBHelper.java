package com.noveogroup.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Schedule data base open helper
 */
public class ScheduleDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "schedule";
    private static final int DATABASE_VERSION = 1;
    private PeriodDAO periodDAO = null;
    private TeacherDAO teacherDAO = null;

    public ScheduleDBHelper(Context context) {
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


    public PeriodDAO getPeriodDAO() throws SQLException {
        if (periodDAO == null) {
            periodDAO = new PeriodDAO(getConnectionSource(), Period.class);
        }
        return periodDAO;
    }

    public TeacherDAO getTeacherDAO() throws SQLException {
        if (teacherDAO == null) {
            teacherDAO = new TeacherDAO(getConnectionSource(), Teacher.class);
        }
        return teacherDAO;
    }

    @Override
    public void close() {
        super.close();
        periodDAO = null;
        teacherDAO = null;
    }

}