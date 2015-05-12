package cz.kutner.comicsdb.task;

import android.os.AsyncTask;

import java.util.List;

import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.Comics;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchComicsListTask
        extends AsyncTask<String, Void, List<Comics>> {
    private String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onPostExecute(List<Comics> result) {
        EventBus.getInstance().post(new ComicsSearchResultEvent(result));
    }

    @Override
    protected List<Comics> doInBackground(String... params) {
        return Utils.getComicsListFromURL(params[0]);
    }
}

