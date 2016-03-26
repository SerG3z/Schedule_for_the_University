package com.sample.drawer;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.sample.drawer.scheduleDataBase.Classroom;
import com.sample.drawer.scheduleDataBase.HelperFactory;
import com.sample.drawer.scheduleDataBase.PeriodTime;
import com.sample.drawer.scheduleDataBase.PeriodType;
import com.sample.drawer.scheduleDataBase.Subject;
import com.sample.drawer.scheduleDataBase.Teacher;

import java.sql.SQLException;

/**
 * An application class (to provide work with Stetho)
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //TODO: не забыть убрать
        //deleteDatabase("schedule.db");

        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);

        HelperFactory.setHelper(getApplicationContext());

        try {
            HelperFactory.getHelper().getSubjectDAO().create(new Subject("Предмет 1"));
            HelperFactory.getHelper().getSubjectDAO().create(new Subject("Предмет 2"));
            HelperFactory.getHelper().getPeriodTypeDAO().create(new PeriodType("Тип 1"));
            HelperFactory.getHelper().getPeriodTypeDAO().create(new PeriodType("Тип 2"));
            HelperFactory.getHelper().getClassroomDAO().create(new Classroom("Аудитория 1"));
            HelperFactory.getHelper().getClassroomDAO().create(new Classroom("Аудитория 2"));
            HelperFactory.getHelper().getPeriodTimeDAO().create(new PeriodTime("12:34","56:78"));
            HelperFactory.getHelper().getPeriodTimeDAO().create(new PeriodTime("87:65","43:21"));
            HelperFactory.getHelper().getTeacherDAO().create(new Teacher("Препод 1"));
            HelperFactory.getHelper().getTeacherDAO().create(new Teacher("Препод 2"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
