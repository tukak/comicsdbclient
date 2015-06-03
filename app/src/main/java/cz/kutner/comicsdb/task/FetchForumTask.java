package cz.kutner.comicsdb.task;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.connector.ForumConnector;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.model.ForumEntry;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class FetchForumTask
        extends AsyncTask<Integer, Void, List<ForumEntry>> {
    private String LOG_TAG = getClass().getSimpleName();

    public FetchForumTask() {

    }

    @Override
    protected void onPostExecute(List<ForumEntry> result) {
        EventBus.getInstance().post(new ForumResultEvent(result));
    }

    @Override
    protected List<ForumEntry> doInBackground(Integer... params) {
        return ForumConnector.get(params[0]);
    }
}

