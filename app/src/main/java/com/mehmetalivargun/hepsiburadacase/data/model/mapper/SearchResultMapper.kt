package com.mehmetalivargun.hepsiburadacase.data.model.mapper

import com.mehmetalivargun.hepsiburadacase.data.model.AppResult
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.model.SearchSoftwareItem
import com.mehmetalivargun.hepsiburadacase.data.model.SearchTrackItem

    fun List<AppResult>?.toSearchAppResponseList()= this?.map{
        it.toSearchSoftwareItem()
    }
    fun List<Result>?.toSearchResultItemResponse()= this?.map{
        it.toSearchResultItemResponse()
    }

    private fun AppResult.toSearchSoftwareItem()= SearchSoftwareItem(
        trackId = trackId!!,
        trackName = trackName,
        price = price,
        releaseDate = releaseDate,
        description = description,
        artworkUrl100 = artworkUrl100,
        primaryGenreName = primaryGenreName,
        sellerName = sellerName,
        kind=kind

    )


    private  fun Result.toSearchResultItemResponse() = SearchTrackItem(
        artworkUrl100 = artworkUrl100,
        collectionId = collectionId,
        trackName = trackName,
        collectionName = collectionName,
        collectionPrice = collectionPrice,
        releaseDate = releaseDate,
        kind = kind,
        price = price,
        previewUrl = previewUrl,
        trackId = trackId!!



    )


