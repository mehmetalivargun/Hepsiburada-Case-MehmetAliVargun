package com.mehmetalivargun.hepsiburadacase.data.model


data class SearchTrackItem(
    val collectionId: Int?,
    val trackName: String?,
    val trackId:Int,
    val collectionName: String?,
    val collectionPrice: Double?,
    val price: Double?,
    val releaseDate: String?,
    val artworkUrl100: String?,
    val kind: String?,
    val previewUrl: String?
)
