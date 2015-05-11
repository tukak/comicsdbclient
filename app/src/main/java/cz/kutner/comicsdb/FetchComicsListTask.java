package cz.kutner.comicsdb;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

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

