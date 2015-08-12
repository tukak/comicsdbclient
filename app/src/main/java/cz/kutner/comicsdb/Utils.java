package cz.kutner.comicsdb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.4.2015.
 */
public class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();

    public static <T> T nvl(T a, T b) {
        return (a == null) ? (T) "" : a;
    }

    public static boolean isConnected() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) ComicsDBApplication.getContext().getSystemService(
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
        return connected;
    }

    public static String fixUrl(String url) {
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return "http://comicsdb.cz/" + url;
        } else {
            return url;
        }
    }
}
