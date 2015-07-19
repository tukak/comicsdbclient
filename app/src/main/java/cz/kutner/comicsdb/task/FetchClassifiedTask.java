package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ClassifiedConnector;
import cz.kutner.comicsdb.event.ClassifiedResultEvent;
import cz.kutner.comicsdb.model.Classified;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class FetchClassifiedTask
        extends AsyncTask<String, Void, List<Classified>> {
    private String LOG_TAG = getClass().getSimpleName();

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

