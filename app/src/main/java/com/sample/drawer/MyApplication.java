package com.sample.drawer;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.sample.drawer.scheduleDataBase.HelperFactory;

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
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
