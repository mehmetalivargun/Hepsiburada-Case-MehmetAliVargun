package com.mehmetalivargun.hepsiburadacase.data.model

import android.os.Parcel
import android.os.Parcelable

data class SearchTrackItem(
    val collectionId: Int?,
    val trackName: String?,
    val collectionName: String?,
    val collectionPrice: Double?,
    val price: Double?,
    val releaseDate: String?,
    val artworkUrl100: String?,
    val kind: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(collectionId)
        parcel.writeString(trackName)
        parcel.writeString(collectionName)
        parcel.writeValue(collectionPrice)
        parcel.writeValue(price)
        parcel.writeString(releaseDate)
        parcel.writeString(artworkUrl100)
        parcel.writeString(kind)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchTrackItem> {
        override fun createFromParcel(parcel: Parcel): SearchTrackItem {
            return SearchTrackItem(parcel)
        }

        override fun newArray(size: Int): Array<SearchTrackItem?> {
            return arrayOfNulls(size)
        }
    }
}
