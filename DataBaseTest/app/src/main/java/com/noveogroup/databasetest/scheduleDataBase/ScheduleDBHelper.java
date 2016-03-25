package com.noveogroup.databasetest.scheduleDataBase;

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

    Classroom.DAO classroomDAO = null;
    Teacher.DAO teacherDAO = null;
    Period.DAO periodDAO = null;
    Day.DAO dayDAO = null;
    PeriodTime.DAO periodTimeDAO = null;
    PeriodType.DAO periodTypeDAO = null;
    Subject.DAO subjectDAO = null;
    Task.DAO taskDAO = null;
    Week.DAO weekDAO = null;

    public ScheduleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public Classroom.DAO getClassroomDAO() {
        if (classroomDAO == null) {
            try {
                classroomDAO = (new Classroom()).new DAO(getConnectionSource(), Classroom.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return classroomDAO;
    }

    public Day.DAO getDayDAO() {
        if (dayDAO == null) {
            try {
                dayDAO = (new Day()).new DAO(getConnectionSource(), Day.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dayDAO;
    }

    public Period.DAO getPeriodDAO() {
        if (periodDAO == null) {
            try {
                periodDAO = (new Period()).new DAO(getConnectionSource(), Period.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return periodDAO;
    }

    public PeriodTime.DAO getPeriodTimeDAO() {
        if (periodTimeDAO == null) {
            try {
                periodTimeDAO = (new PeriodTime()).new DAO(getConnectionSource(), PeriodTime.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return periodTimeDAO;
    }

    public PeriodType.DAO getPeriodTypeDAO() {
        if (periodTypeDAO == null) {
            try {
                periodTypeDAO = (new PeriodType()).new DAO(getConnectionSource(), PeriodType.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return periodTypeDAO;
    }

    public Subject.DAO getSubjectDAO() {
        if (subjectDAO == null) {
            try {
                subjectDAO = (new Subject()).new DAO(getConnectionSource(), Subject.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return subjectDAO;
    }

    public Task.DAO getTaskDAO() {
        if (taskDAO == null) {
            try {
                taskDAO = (new Task()).new DAO(getConnectionSource(), Task.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return taskDAO;
    }

    public Teacher.DAO getTeacherDAO() {
        if (teacherDAO == null) {
            try {
                teacherDAO = (new Teacher()).new DAO(getConnectionSource(), Teacher.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return teacherDAO;
    }

    public Week.DAO getWeekDAO() {
        if (weekDAO == null) {
            try {
                weekDAO = (new Week()).new DAO(getConnectionSource(), Week.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return weekDAO;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Classroom.class);
            TableUtils.createTable(connectionSource, Teacher.class);
            TableUtils.createTable(connectionSource, Period.class);
            TableUtils.createTable(connectionSource, Day.class);
            TableUtils.createTable(connectionSource, PeriodTime.class);
            TableUtils.createTable(connectionSource, PeriodType.class);
            TableUtils.createTable(connectionSource, Subject.class);
            TableUtils.createTable(connectionSource, Task.class);
            TableUtils.createTable(connectionSource, Week.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Classroom.class, false);
            TableUtils.dropTable(connectionSource, Teacher.class, false);
            TableUtils.dropTable(connectionSource, Period.class, false);
            TableUtils.dropTable(connectionSource, Day.class, false);
            TableUtils.dropTable(connectionSource, PeriodTime.class, false);
            TableUtils.dropTable(connectionSource, PeriodType.class, false);
            TableUtils.dropTable(connectionSource, Subject.class, false);
            TableUtils.dropTable(connectionSource, Task.class, false);
            TableUtils.dropTable(connectionSource, Week.class, false);

            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() {
        super.close();
        Classroom.DAO classroomDAO = null;
        Teacher.DAO teacherDAO = null;
        Period.DAO periodDAO = null;
        Day.DAO dayDAO = null;
        PeriodTime.DAO periodTimeDAO = null;
        PeriodType.DAO periodTypeDAO = null;
        Subject.DAO subjectDAO = null;
        Task.DAO taskDAO = null;
        Week.DAO weekDAO = null;
    }


}