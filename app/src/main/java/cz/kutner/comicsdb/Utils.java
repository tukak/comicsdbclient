package cz.kutner.comicsdb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static <T> T nvl(T a, T b) {
        return (a == null) ? (T) "" : a;
    }

    public static boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) ComicsDBApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String fixUrl(String url) {
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return "http://comicsdb.cz/" + url;
        } else {
            return url;
        }
    }
}
