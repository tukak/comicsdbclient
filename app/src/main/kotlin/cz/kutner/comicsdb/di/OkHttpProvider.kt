package cz.kutner.comicsdb.di

import okhttp3.OkHttpClient

object OkHttpProvider {
    val okHttpClient by lazy { OkHttpClient() }
}