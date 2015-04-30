package cz.kutner.comicsdb;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 30.4.2015.
 */
abstract class FetchLoadingAsyncTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {
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
}
