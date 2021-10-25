package com.mehmetalivargun.hepsiburadacase.data.remote

import com.mehmetalivargun.hepsiburadacase.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {

    @GET("search")
    suspend fun search(@Query("term") term:String,
                       @Query("entity") entity:String?): Response<SearchResponse>

}