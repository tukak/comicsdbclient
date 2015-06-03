package cz.kutner.comicsdb.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cz.kutner.comicsdb.connector.ComicsListConnector;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.model.Comics;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchComicsListTask
        extends AsyncTask<String, Void, List<Comics>> {
    private String LOG_TAG = getClass().getSimpleName();

    public final static String SEARCH = "search";
    public final static String LIST = "list";
    @Override
    protected void onPostExecute(List<Comics> result) {
        EventBus.getInstance().post(new ComicsSearchResultEvent(result));
    }

    @Override
    protected List<Comics> doInBackground(String... params) {
        List<Comics> result = null;
        switch (params[0]) {
            case SEARCH:
                result = ComicsListConnector.search(params[1]);
                break;
            case LIST:
                result = ComicsListConnector.get(Integer.parseInt(params[1]));
                break;
        }
        return result;
    }
}

