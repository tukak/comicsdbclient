package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.AuthorConnector;
import cz.kutner.comicsdb.event.AuthorResultEvent;
import cz.kutner.comicsdb.model.Author;

public class FetchAuthorTask
        extends AsyncTask<String, Void, List<Author>> {

    public final static String SEARCH = "search";
    public final static String LIST = "list";

    @Override

    protected void onPostExecute(List<Author> result) {
        ComicsDBApplication.getEventBus().post(new AuthorResultEvent(result));
    }

    @Override

    protected List<Author> doInBackground(String... params) {
        List<Author> result = null;
        result = AuthorConnector.get(Integer.parseInt(params[1]));
        return result;
    }
}

