package cz.kutner.comicsdb.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkModule(private val applicationContext: Context) {
    fun isConnected(): Boolean {
        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}