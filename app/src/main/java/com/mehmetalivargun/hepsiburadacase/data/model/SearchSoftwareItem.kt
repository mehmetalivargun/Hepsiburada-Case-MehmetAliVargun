package com.mehmetalivargun.hepsiburadacase.data.model


data class SearchSoftwareItem(
    val trackId:Int,
    val trackName:String?,
    val price:Double?,
    val releaseDate : String?,
    val description:String?,
    val artworkUrl100:String?,
    val primaryGenreName:String?,
    val sellerName:String?,
    val kind:String?
)

