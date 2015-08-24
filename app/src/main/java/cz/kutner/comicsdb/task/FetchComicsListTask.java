package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ComicsListConnector;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.model.Comics;

public class FetchComicsListTask
        extends AsyncTask<String, Void, List<Comics>> {

    public final static String SEARCH = "search";
    public final static String LIST = "list";

    @Override

    protected void onPostExecute(List<Comics> result) {
        ComicsDBApplication.getEventBus().post(new ComicsSearchResultEvent(result));
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

