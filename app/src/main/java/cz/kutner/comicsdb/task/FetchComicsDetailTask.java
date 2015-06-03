package cz.kutner.comicsdb.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.connector.ComicsConnector;
import cz.kutner.comicsdb.event.ComicsDetailResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.model.Comment;
import cz.kutner.comicsdbclient.comicsdbclient.R;

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
        EventBus.getInstance().post(new ComicsDetailResultEvent(listResult));
    }

    @Override
    protected Comics doInBackground(Integer... params) {
        return ComicsConnector.get(params[0]);
    }
}
