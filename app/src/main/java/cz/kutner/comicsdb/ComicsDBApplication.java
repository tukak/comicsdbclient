package cz.kutner.comicsdb;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class ComicsDBApplication extends android.app.Application {
    private static Context context;
    private static Bus eventBus;
    private static Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        }
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
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