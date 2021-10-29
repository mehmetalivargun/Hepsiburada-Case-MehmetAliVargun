package com.mehmetalivargun.hepsiburadacase.data.model

data class SearchResponse<T>(
    val resultCount: Int,
    val results: List<T> = listOf()
)