package cz.kutner.comicsdb

import android.content.Context
import android.net.ConnectivityManager

public object Utils {

    public fun isConnected(): Boolean {
        val cm = ComicsDBApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    public fun fixUrl(url: String): String {
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return "http://comicsdb.cz/" + url
        } else {
            return url
        }
    }
}
