package cz.kutner.comicsdb

import android.content.Context
import android.net.ConnectivityManager

public object Utils {

    public fun isConnected(): Boolean {
        val cm = ComicsDBApplication.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    public fun fixUrl(url: String, prefix: String = ""): String {
        val real_prefix = if (prefix.length > 0) prefix else ComicsDBApplication.context?.getString(R.string.url_comicsdb)
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return real_prefix + url
        } else {
            return url
        }
    }
}
