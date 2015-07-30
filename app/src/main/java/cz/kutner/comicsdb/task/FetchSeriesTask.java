package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ComicsListConnector;
import cz.kutner.comicsdb.connector.SeriesConnector;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.event.SeriesResultEvent;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.model.Series;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchSeriesTask
        extends AsyncTask<String, Void, List<Series>> {
    private String LOG_TAG = getClass().getSimpleName();

    public final static String SEARCH = "search";
    public final static String LIST = "list";

    @Override
    @DebugLog
    protected void onPostExecute(List<Series> result) {
        ComicsDBApplication.getEventBus().post(new SeriesResultEvent(result));
    }

    @Override
    @DebugLog
    protected List<Series> doInBackground(String... params) {
        List<Series> result = null;
        result = SeriesConnector.get(Integer.parseInt(params[1]));
        return result;
    }
}

