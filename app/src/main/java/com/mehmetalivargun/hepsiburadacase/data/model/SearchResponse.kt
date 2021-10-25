package com.mehmetalivargun.hepsiburadacase.data.model

data class SearchResponse(
    val resultCount: Int,
    val results: List<Result>
)