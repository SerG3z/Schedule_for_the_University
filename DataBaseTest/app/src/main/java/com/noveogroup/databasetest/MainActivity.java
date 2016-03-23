package com.noveogroup.databasetest;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
import com.j256.ormlite.android.apptools.OrmLiteQueryForAllLoader;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    static final int LOADER_PERIOD_ALL_ID = 1;
    static final int LOADER_TEACHER_QUERY_ID = 2;
    static final String ARG_TEACHER = "teacher";
    private ScheduleDataBaseHelper helper;
    TeacherDAO teacherDao;
    PeriodDAO periodDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = OpenHelperManager
                .getHelper(getApplicationContext(), ScheduleDataBaseHelper.class);

        try {
            teacherDao = helper.getTeacherDAO();
            periodDAO = helper.getPeriodDAO();
            teacherDao.create(new Teacher("Smoke W."));
            teacherDao.create(new Teacher("Fuss O."));
            teacherDao.create(new Teacher("Poor G."));

            getLoaderManager().initLoader(LOADER_PERIOD_ALL_ID, null, this);
            Bundle bundle = new Bundle();
            bundle.putString(ARG_TEACHER, "Smoke W.");
            getLoaderManager().initLoader(LOADER_TEACHER_QUERY_ID, bundle, this);
            bundle.putString(ARG_TEACHER, "Fuss O.");
            getLoaderManager().initLoader(LOADER_TEACHER_QUERY_ID, bundle, this);
            bundle.putString(ARG_TEACHER, "Poor G.");
            getLoaderManager().initLoader(LOADER_TEACHER_QUERY_ID, bundle, this)

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        switch (id) {
            case LOADER_PERIOD_ALL_ID:
                loader = new OrmLiteQueryForAllLoader<Period, Integer>(this,periodDAO);
                break;
            case LOADER_TEACHER_QUERY_ID:
                try {
                    PreparedQuery<Teacher> query = teacherDao
                            .getTeacherByNameQuery(args.getString(ARG_TEACHER));
                    loader = new OrmLitePreparedQueryLoader<>(this, teacherDao, query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()) {
            case LOADER_PERIOD_ALL_ID:
                List<Period> periods = (List<Period>) data;
                List<String> periodsStr = new ArrayList<>();
                for (Period p : periods) {
                    periodsStr.add(p.toString());
                }
                ArrayAdapter<String> adapter
                        = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, periodsStr);
                ListView listView = (ListView) findViewById(R.id.periods_lv);
                listView.setAdapter(adapter);
                break;
            case LOADER_TEACHER_QUERY_ID:
                List<Teacher> teacher = (List<Teacher>) data;
                if (teacher.size() != 0) {
                    Teacher t = teacher.get(0);
                    try {
                        if (t.getTeacherName().compareTo("Smoke W.") == 0) {
                            periodDAO.create(new Period("Math", t, "08:00-09:35",
                                    "Homework"));

                        } else if (t.getTeacherName().compareTo("Fuss O.") == 0) {
                            periodDAO.create(new Period("Physics", t, "11:40-13:15",
                                    "Lab 1"));
                        } else if (t.getTeacherName().compareTo("Poor G.") == 0) {
                            periodDAO.create(new Period("Physics", t, "11:40-13:15",
                                    "Lab 1"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

}
