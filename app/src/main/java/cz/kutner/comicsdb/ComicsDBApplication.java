package cz.kutner.comicsdb;

import android.content.Context;

import com.squareup.otto.Bus;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 19.7.2015.
 */
public class ComicsDBApplication extends android.app.Application {
    private static Context context;
    private static Bus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        eventBus = new Bus();
    }

    public static Context getContext() {
        return context;
    }

    public static Bus getEventBus() {
        return eventBus;
    }
}