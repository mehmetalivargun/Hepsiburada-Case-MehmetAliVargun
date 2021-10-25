package com.mehmetalivargun.hepsiburadacase.data.model

data class SoftwareSearchResponse(
    val resultCount: Int,
    val appResults: List<AppResult>
)