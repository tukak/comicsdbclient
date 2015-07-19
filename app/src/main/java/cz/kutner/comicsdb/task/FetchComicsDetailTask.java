package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ComicsConnector;
import cz.kutner.comicsdb.event.ComicsDetailResultEvent;
import cz.kutner.comicsdb.model.Comics;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchComicsDetailTask extends AsyncTask<Integer, Void, Comics> {
    private String LOG_TAG = getClass().getSimpleName();

    public FetchComicsDetailTask() {
    }

    @Override
    protected void onPostExecute(Comics result) {
        List<Comics> listResult = new ArrayList<>();
        listResult.add(result);
        ComicsDBApplication.getEventBus().post(new ComicsDetailResultEvent(listResult));
    }

    @Override
    protected Comics doInBackground(Integer... params) {
        return ComicsConnector.get(params[0]);
    }
}
