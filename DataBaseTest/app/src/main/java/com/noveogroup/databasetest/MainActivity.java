package com.noveogroup.databasetest;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.stetho.Stetho;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
import com.j256.ormlite.android.apptools.OrmLiteQueryForAllLoader;
import com.j256.ormlite.stmt.PreparedQuery;
import com.noveogroup.databasetest.scheduleDataBase.Classroom;
import com.noveogroup.databasetest.scheduleDataBase.Day;
import com.noveogroup.databasetest.scheduleDataBase.Period;
import com.noveogroup.databasetest.scheduleDataBase.PeriodTime;
import com.noveogroup.databasetest.scheduleDataBase.PeriodType;
import com.noveogroup.databasetest.scheduleDataBase.ScheduleDBHelper;
import com.noveogroup.databasetest.scheduleDataBase.Subject;
import com.noveogroup.databasetest.scheduleDataBase.Task;
import com.noveogroup.databasetest.scheduleDataBase.Teacher;
import com.noveogroup.databasetest.scheduleDataBase.Week;
import com.noveogroup.databasetest.util.LoaderIdManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int LOADER_PERIOD_ALL_ID = 1;
    //static final int LOADER_TEACHER_QUERY_ID = 2;
    //static final String ARG_TEACHER = "teacher";

    private ScheduleDBHelper helper;
    private LoaderIdManager idManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = OpenHelperManager
                .getHelper(getApplicationContext(), ScheduleDBHelper.class);
        idManager = new LoaderIdManager();
        helper.getPeriodDAO();

        try {
            Subject subject = new Subject("Социология");
            helper.getSubjectDAO().create(subject);
            Teacher teacher = new Teacher("Микиденко Н.Л.");
            helper.getTeacherDAO().create(teacher);
            PeriodType periodType = new PeriodType("лекция");
            helper.getPeriodTypeDAO().create(periodType);
            Classroom classroom = new Classroom("178 н.к.");
            helper.getClassroomDAO().create(classroom);
            PeriodTime periodTime = new PeriodTime(4, "13:15", "15:20");
            helper.getPeriodTimeDAO().create(periodTime);
            Calendar date = Calendar.getInstance();
            date.set(2016, 3, 28);
            Day day = new Day(new Date(date.getTimeInMillis()));
            helper.getDayDAO().create(day);
            Period period = new Period.Builder(subject,periodTime,true, false)
                    .day(day).teacher(teacher).type(periodType).сlassroom(classroom).build();
            helper.getPeriodDAO().create(period);

            Week week = new Week(8, true, helper.getWeekDAO());
            week.addDay(day);
            helper.getWeekDAO().create(week);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        getLoaderManager().initLoader(idManager.grabId(), null, new LoaderManager.LoaderCallbacks<List<Period>>() {
            @Override
            public Loader<List<Period>> onCreateLoader(int id, Bundle args) {
                return new OrmLiteQueryForAllLoader<Period, Integer>(getBaseContext(), helper.getPeriodDAO());
            }

            @Override
            public void onLoadFinished(Loader<List<Period>> loader, List<Period> data) {
                List<Period> periods = data;
                List<String> periodsStr = new ArrayList<>();
                for (Period p : periods) {
                    periodsStr.add(p.toString());
                }
                ArrayAdapter<String> adapter
                        = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, periodsStr);
                ListView listView = (ListView) findViewById(R.id.periods_lv);
                listView.setAdapter(adapter);
                idManager.releaseId(getTaskId());
            }

            @Override
            public void onLoaderReset(Loader<List<Period>> loader) {

            }
        });

    }

}
