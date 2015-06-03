package cz.kutner.comicsdb.task;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.connector.ClassifiedConnector;
import cz.kutner.comicsdb.event.ClassifiedResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.model.Classified;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class FetchClassifiedTask
        extends AsyncTask<Integer, Void, List<Classified>> {
    private String LOG_TAG = getClass().getSimpleName();

    public FetchClassifiedTask() {
    }

    @Override
    protected void onPostExecute(List<Classified> result) {
        EventBus.getInstance().post(new ClassifiedResultEvent(result));
    }

    @Override
    protected List<Classified> doInBackground(Integer... params) {
        return ClassifiedConnector.get(params[0]);
    }
}

