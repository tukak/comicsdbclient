package cz.kutner.comicsdb;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Bus;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 19.7.2015.
 */
public class ComicsDBApplication extends android.app.Application {
    private static Context context;
    private static Bus eventBus;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        analytics = GoogleAnalytics.getInstance(this);
        tracker = analytics.newTracker(getString(R.string.google_analytics_id));
        tracker.enableExceptionReporting(true);
        context = getApplicationContext();
        eventBus = new Bus();
    }

    public static Context getContext() {
        return context;
    }

    public static Bus getEventBus() {
        return eventBus;
    }

    public static Tracker getTracker() {
        return tracker;
    }
}