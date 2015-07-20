package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ComicsListConnector;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.model.Comics;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchComicsListTask
        extends AsyncTask<String, Void, List<Comics>> {
    private String LOG_TAG = getClass().getSimpleName();

    public final static String SEARCH = "search";
    public final static String LIST = "list";

    @Override
    @DebugLog
    protected void onPostExecute(List<Comics> result) {
        ComicsDBApplication.getEventBus().post(new ComicsSearchResultEvent(result));
    }

    @Override
    @DebugLog
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

