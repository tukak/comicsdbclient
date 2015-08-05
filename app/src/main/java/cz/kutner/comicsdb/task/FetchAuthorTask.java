package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.AuthorConnector;
import cz.kutner.comicsdb.event.AuthorResultEvent;
import cz.kutner.comicsdb.model.Author;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchAuthorTask
        extends AsyncTask<String, Void, List<Author>> {
    private String LOG_TAG = getClass().getSimpleName();

    public final static String SEARCH = "search";
    public final static String LIST = "list";

    @Override
    @DebugLog
    protected void onPostExecute(List<Author> result) {
        ComicsDBApplication.getEventBus().post(new AuthorResultEvent(result));
    }

    @Override
    @DebugLog
    protected List<Author> doInBackground(String... params) {
        List<Author> result = null;
        result = AuthorConnector.get(Integer.parseInt(params[1]));
        return result;
    }
}

