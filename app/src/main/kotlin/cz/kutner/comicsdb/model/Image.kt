package cz.kutner.comicsdb.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Image(val previewUrl: String, val fullUrl: String, val caption: String) : Parcelable