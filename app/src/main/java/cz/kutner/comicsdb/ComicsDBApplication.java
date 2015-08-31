package cz.kutner.comicsdb;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import cz.kutner.comicsdb.service.AuthorConverter;
import cz.kutner.comicsdb.service.AuthorService;
import cz.kutner.comicsdb.service.NewsConverter;
import cz.kutner.comicsdb.service.NewsService;
import cz.kutner.comicsdb.service.SeriesConverter;
import cz.kutner.comicsdb.service.SeriesService;
import retrofit.RestAdapter;
import timber.log.Timber;

public class ComicsDBApplication extends android.app.Application {
    private static Context context;
    private static Tracker tracker;
    private static SeriesService seriesService;
    private static AuthorService authorService;
    private static NewsService newsService;


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
    }


    public static Context getContext() {
        return context;
    }

    public static Tracker getTracker() {
        return tracker;
    }

    public static SeriesService getSeriesService() {
        if (seriesService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.comicsdb.cz")
                    .setConverter(new SeriesConverter())
                    .build();
            seriesService = restAdapter.create(SeriesService.class);
        }
        return seriesService;
    }

    public static AuthorService getAuthorService() {
        if (authorService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.comicsdb.cz")
                    .setConverter(new AuthorConverter())
                    .build();
            authorService = restAdapter.create(AuthorService.class);
        }
        return authorService;
    }

    public static NewsService getNewsService() {
        if (newsService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.comicsdb.cz")
                    .setConverter(new NewsConverter())
                    .build();
            newsService = restAdapter.create(NewsService.class);
        }
        return newsService;
    }
}