package com.sample.drawer;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.sample.drawer.scheduleDataBase.Classroom;
import com.sample.drawer.scheduleDataBase.HelperFactory;
import com.sample.drawer.scheduleDataBase.PeriodTime;
import com.sample.drawer.scheduleDataBase.PeriodType;
import com.sample.drawer.scheduleDataBase.Subject;
import com.sample.drawer.scheduleDataBase.Teacher;
import com.sample.drawer.utils.ScheduleInitializer;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * An application class (to provide work with Stetho)
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //TODO: не забыть убрать
        deleteDatabase("schedule.db");

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

        ScheduleInitializer.insertDefaultData(getResources());
        try {
            ScheduleInitializer.initializeSchedule(new GregorianCalendar(2016,1,8), true, 17, new int[]{7, 14});
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
