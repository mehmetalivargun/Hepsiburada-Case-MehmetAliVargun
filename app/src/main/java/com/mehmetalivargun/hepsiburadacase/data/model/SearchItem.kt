package com.mehmetalivargun.hepsiburadacase.data.model


data class SearchItem(
    val trackName: String?,
    val trackId:Int,
    val price: Double?,
    val releaseDate: String?,
    val artworkUrl100: String?,
    val kind: String?,
)
