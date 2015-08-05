package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.NewsConnector;
import cz.kutner.comicsdb.event.NewsResultEvent;
import cz.kutner.comicsdb.model.NewsItem;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchNewsTask
        extends AsyncTask<String, Void, List<NewsItem>> {
    private String LOG_TAG = getClass().getSimpleName();

    public final static String SEARCH = "search";
    public final static String LIST = "list";

    @Override
    @DebugLog
    protected void onPostExecute(List<NewsItem> result) {
        ComicsDBApplication.getEventBus().post(new NewsResultEvent(result));
    }

    @Override
    @DebugLog
    protected List<NewsItem> doInBackground(String... params) {
        List<NewsItem> result = null;
        result = NewsConnector.getNews();
        return result;
    }
}

