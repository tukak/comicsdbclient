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

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 30.4.2015.
 */
abstract class FetchLoadingAsyncTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {
    private static final String LOG_TAG = FetchLoadingAsyncTask.class.getSimpleName();

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
        Log.i(LOG_TAG, "spoustime pre");
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            connected = true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            connected = true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            connected = true;
        }

        if (!connected) {
            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.loading_error, container, false);
            container.removeAllViews();
            container.addView(view);
            this.cancel(true);
            //TODO nemaze se nacitaci kolecko
            //TODO opakování akce
        }
    }
}
