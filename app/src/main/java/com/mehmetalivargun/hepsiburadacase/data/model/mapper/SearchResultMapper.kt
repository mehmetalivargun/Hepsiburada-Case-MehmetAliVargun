package com.mehmetalivargun.hepsiburadacase.data.model.mapper

import com.mehmetalivargun.hepsiburadacase.data.model.*


fun List<Result>?.toSearchResultItemResponse() = this?.map {
    it.toSearchResultItemResponse()
}

private fun Result.toSearchResultItemResponse() = SearchItem(
    trackId = trackId!!,
    trackName = trackName,
    price = trackPrice,
    releaseDate = releaseDate,
    artworkUrl100 = artworkUrl100,
    kind = kind

)


