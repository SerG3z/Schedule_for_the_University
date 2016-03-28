package com.sample.drawer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sample.drawer.database.dao.ClassroomDAO;
import com.sample.drawer.database.dao.DayDAO;
import com.sample.drawer.database.dao.DayPeriodDAO;
import com.sample.drawer.database.dao.PeriodDAO;
import com.sample.drawer.database.dao.PeriodTimeDAO;
import com.sample.drawer.database.dao.PeriodTypeDAO;
import com.sample.drawer.database.dao.SubjectDAO;
import com.sample.drawer.database.dao.TaskDAO;
import com.sample.drawer.database.dao.TeacherDAO;
import com.sample.drawer.database.dao.WeekDAO;

import java.sql.SQLException;

/**
 * Schedule data base open helper
 */
public class ScheduleDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = ScheduleDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "schedule.db";
    private static final int DATABASE_VERSION = 1;

    private ClassroomDAO classroomDAO = null;
    private TeacherDAO teacherDAO = null;
    private PeriodDAO periodDAO = null;
    private DayDAO dayDAO = null;
    private PeriodTimeDAO periodTimeDAO = null;
    private PeriodTypeDAO periodTypeDAO = null;
    private SubjectDAO subjectDAO = null;
    private TaskDAO taskDAO = null;
    private WeekDAO weekDAO = null;
    private DayPeriodDAO dayPeriodDAO = null;

    public ScheduleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ClassroomDAO getClassroomDAO() {
        if (classroomDAO == null) {
            try {
                classroomDAO = new ClassroomDAO(getConnectionSource(), Classroom.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return classroomDAO;
    }

    public DayDAO getDayDAO() {
        if (dayDAO == null) {
            try {
                dayDAO = new DayDAO(getConnectionSource(), Day.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dayDAO;
    }

    public PeriodDAO getPeriodDAO() {
        if (periodDAO == null) {
            try {
                periodDAO = new PeriodDAO(getConnectionSource(), Period.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return periodDAO;
    }

    public PeriodTimeDAO getPeriodTimeDAO() {
        if (periodTimeDAO == null) {
            try {
                periodTimeDAO = new PeriodTimeDAO(getConnectionSource(), PeriodTime.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return periodTimeDAO;
    }

    public PeriodTypeDAO getPeriodTypeDAO() {
        if (periodTypeDAO == null) {
            try {
                periodTypeDAO = new PeriodTypeDAO(getConnectionSource(), PeriodType.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return periodTypeDAO;
    }

    public SubjectDAO getSubjectDAO() {
        if (subjectDAO == null) {
            try {
                subjectDAO = new SubjectDAO(getConnectionSource(), Subject.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return subjectDAO;
    }

    public TaskDAO getTaskDAO() {
        if (taskDAO == null) {
            try {
                taskDAO = new TaskDAO(getConnectionSource(), Task.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return taskDAO;
    }

    public TeacherDAO getTeacherDAO() {
        if (teacherDAO == null) {
            try {
                teacherDAO = new TeacherDAO(getConnectionSource(), Teacher.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return teacherDAO;
    }

    public WeekDAO getWeekDAO() {
        if (weekDAO == null) {
            try {
                weekDAO = new WeekDAO(getConnectionSource(), Week.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return weekDAO;
    }

    public DayPeriodDAO getDayPeriodDAO() {
        if (dayPeriodDAO == null) {
            try {
                dayPeriodDAO = new DayPeriodDAO(getConnectionSource(), DayPeriod.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dayPeriodDAO;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Subject.class);
            TableUtils.createTable(connectionSource, Teacher.class);
            TableUtils.createTable(connectionSource, PeriodTime.class);
            TableUtils.createTable(connectionSource, PeriodType.class);
            TableUtils.createTable(connectionSource, Classroom.class);
            TableUtils.createTable(connectionSource, Day.class);
            TableUtils.createTable(connectionSource, Week.class);
            TableUtils.createTable(connectionSource, Period.class);
            TableUtils.createTable(connectionSource, Task.class);
            TableUtils.createTable(connectionSource, DayPeriod.class);

        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
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
            TableUtils.dropTable(connectionSource, DayPeriod.class, false);

            onCreate(database, connectionSource);

        } catch (SQLException e) {
            Log.e(TAG, "Error upgrading db " + DATABASE_NAME + "from ver " + oldVersion);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
        classroomDAO = null;
        teacherDAO = null;
        periodDAO = null;
        dayDAO = null;
        periodTimeDAO = null;
        periodTypeDAO = null;
        subjectDAO = null;
        taskDAO = null;
        weekDAO = null;
    }


}