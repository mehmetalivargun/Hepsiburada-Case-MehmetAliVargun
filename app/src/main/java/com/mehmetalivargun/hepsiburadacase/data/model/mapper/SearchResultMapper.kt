package com.mehmetalivargun.hepsiburadacase.data.model.mapper

import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.model.SearchResultItemResponse

class SearchResultMapper {

    fun toSearchResponseList(json: List<Result>?): List<SearchResultItemResponse> {
        with(json) {
            return if (this?.isNotEmpty() == true) {
                this.map { toSearchResultItemResponse(it) }
            } else {
                emptyList()
            }
        }
    }


private fun toSearchResultItemResponse(json: Result): SearchResultItemResponse {
    with(json) {
        return SearchResultItemResponse(

            artworkUrl100 = artworkUrl100


        )
    }
}

}