package cz.kutner.comicsdb;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 30.4.2015.
 */
abstract class FetchLoadingAsyncTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {
    private final String LOG_TAG = this.getClass().getSimpleName();

    Activity activity;
    Integer successLayout;
    ViewGroup container;

    public FetchLoadingAsyncTask(Activity activity, Integer successLayout, ViewGroup container) {
        this.activity = activity;
        this.successLayout = successLayout;
        this.container = container;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(successLayout, container, false);
        container.removeAllViews();
        container.addView(view);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!Utils.isConnected(activity)) {
            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.loading_error, container, false);
            container.removeAllViews();
            container.addView(view);
            this.cancel(true);
        } else {
            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.loading, container, false);
            container.removeAllViews();
            container.addView(view);
        }
    }
}
