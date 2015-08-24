package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ClassifiedConnector;
import cz.kutner.comicsdb.event.ClassifiedResultEvent;
import cz.kutner.comicsdb.model.Classified;

public class FetchClassifiedTask
        extends AsyncTask<String, Void, List<Classified>> {

    public FetchClassifiedTask() {
    }

    @Override
    protected void onPostExecute(List<Classified> result) {
        ComicsDBApplication.getEventBus().post(new ClassifiedResultEvent(result));
    }

    @Override
    protected List<Classified> doInBackground(String... params) {
        return ClassifiedConnector.getFiltered(Integer.valueOf(params[0]), params[1], params[2]);
    }
}

