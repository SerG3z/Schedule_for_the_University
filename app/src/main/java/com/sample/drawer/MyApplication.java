package com.sample.drawer;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.ScheduleInitializer;
import com.vk.sdk.VKSdk;

import java.sql.SQLException;
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

        try {
            ScheduleInitializer.initializeSchedule(new GregorianCalendar(2016,1,8), true, 17, new int[]{7, 14});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ScheduleInitializer.insertDefaultData(getResources());

        VKSdk.initialize(this);
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
