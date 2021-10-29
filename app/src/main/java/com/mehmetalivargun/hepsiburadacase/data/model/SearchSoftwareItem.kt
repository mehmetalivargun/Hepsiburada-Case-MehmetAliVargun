package com.mehmetalivargun.hepsiburadacase.data.model

import android.os.Parcel
import android.os.Parcelable

data class SearchSoftwareItem(
    val trackId:Int?,
    val trackName:String?,
    val price:Double?,
    val releaseDate : String?,
    val description:String?,
    val artworkUrl100:String?,
    val primaryGenreName:String?,
    val sellerName:String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(trackId)
        parcel.writeString(trackName)
        parcel.writeValue(price)
        parcel.writeString(releaseDate)
        parcel.writeString(description)
        parcel.writeString(artworkUrl100)
        parcel.writeString(primaryGenreName)
        parcel.writeString(sellerName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchSoftwareItem> {
        override fun createFromParcel(parcel: Parcel): SearchSoftwareItem {
            return SearchSoftwareItem(parcel)
        }

        override fun newArray(size: Int): Array<SearchSoftwareItem?> {
            return arrayOfNulls(size)
        }
    }
}
