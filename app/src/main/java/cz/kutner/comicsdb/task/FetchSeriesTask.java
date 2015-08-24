package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.SeriesConnector;
import cz.kutner.comicsdb.event.SeriesResultEvent;
import cz.kutner.comicsdb.model.Series;

public class FetchSeriesTask
        extends AsyncTask<String, Void, List<Series>> {

    public final static String SEARCH = "search";
    public final static String LIST = "list";

    @Override

    protected void onPostExecute(List<Series> result) {
        ComicsDBApplication.getEventBus().post(new SeriesResultEvent(result));
    }

    @Override

    protected List<Series> doInBackground(String... params) {
        List<Series> result = null;
        result = SeriesConnector.get(Integer.parseInt(params[1]));
        return result;
    }
}

