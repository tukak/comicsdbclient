package cz.kutner.comicsdb.model

import android.os.Parcel
import android.os.Parcelable

data class Image(val previewUrl:String, val fullUrl:String, val caption:String) : Parcelable {
    constructor(source: Parcel): this(source.readString(), source.readString(), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(previewUrl)
        dest?.writeString(fullUrl)
        dest?.writeString(caption)
    }

    companion object {
        @Suppress("unused")
        @JvmField val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {
            override fun createFromParcel(source: Parcel): Image {
                return Image(source)
            }

            override fun newArray(size: Int): Array<Image?> {
                return arrayOfNulls(size)
            }
        }
    }
}