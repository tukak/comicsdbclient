package cz.kutner.comicsdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(val previewUrl: String, val fullUrl: String, val caption: String) : Parcelable