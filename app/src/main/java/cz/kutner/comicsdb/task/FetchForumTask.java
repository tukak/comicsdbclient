package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ForumConnector;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.model.ForumEntry;

public class FetchForumTask
        extends AsyncTask<String, Void, List<ForumEntry>> {

    public FetchForumTask() {

    }

    @Override
    protected void onPostExecute(List<ForumEntry> result) {
        ComicsDBApplication.getEventBus().post(new ForumResultEvent(result));
    }

    @Override
    protected List<ForumEntry> doInBackground(String... params) {
        return ForumConnector.getFiltered(Integer.valueOf(params[0]), params[1], params[2]);
    }
}

