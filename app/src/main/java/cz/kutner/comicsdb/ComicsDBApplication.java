package cz.kutner.comicsdb;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import cz.kutner.comicsdb.connector.converter.AuthorConverter;
import cz.kutner.comicsdb.connector.converter.ClassifiedConverter;
import cz.kutner.comicsdb.connector.converter.ComicsConverter;
import cz.kutner.comicsdb.connector.converter.ComicsListConverter;
import cz.kutner.comicsdb.connector.converter.ForumConverter;
import cz.kutner.comicsdb.connector.converter.NewsConverter;
import cz.kutner.comicsdb.connector.converter.SeriesConverter;
import cz.kutner.comicsdb.connector.service.AuthorService;
import cz.kutner.comicsdb.connector.service.ClassifiedService;
import cz.kutner.comicsdb.connector.service.ComicsListService;
import cz.kutner.comicsdb.connector.service.ComicsService;
import cz.kutner.comicsdb.connector.service.ForumService;
import cz.kutner.comicsdb.connector.service.NewsService;
import cz.kutner.comicsdb.connector.service.SeriesService;
import io.fabric.sdk.android.Fabric;
import retrofit.RestAdapter;
import timber.log.Timber;

public class ComicsDBApplication extends android.app.Application {
    private static Context context;
    private static Tracker tracker;
    private static SeriesService seriesService;
    private static AuthorService authorService;
    private static NewsService newsService;
    private static ForumService forumService;
    private static ClassifiedService classifiedService;
    private static ComicsListService comicsListService;
    private static ComicsService comicsService;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build());
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

    public static ForumService getForumService() {
        if (forumService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.comicsdb.cz")
                    .setConverter(new ForumConverter())
                    .build();
            forumService = restAdapter.create(ForumService.class);
        }
        return forumService;
    }

    public static ClassifiedService getClassifiedService() {
        if (classifiedService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.comicsdb.cz")
                    .setConverter(new ClassifiedConverter())
                    .build();
            classifiedService = restAdapter.create(ClassifiedService.class);
        }
        return classifiedService;
    }

    public static ComicsListService getComicsListService() {
        if (comicsListService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.comicsdb.cz")
                    .setConverter(new ComicsListConverter())
                    .build();
            comicsListService = restAdapter.create(ComicsListService.class);
        }
        return comicsListService;
    }

    public static ComicsService getComicsService() {
        if (comicsService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://www.comicsdb.cz")
                    .setConverter(new ComicsConverter())
                    .build();
            comicsService = restAdapter.create(ComicsService.class);
        }
        return comicsService;
    }
}