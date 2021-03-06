package com.mehmetalivargun.hepsiburadacase.data.remote

import com.mehmetalivargun.hepsiburadacase.data.model.AppResult
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.model.SearchResponse
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {

    @GET("search")
    suspend fun search(@Query("term") term:String,
                       @Query("entity") entity:String?,@Query("offset") offset:Int,@Query("limit") limit :Int): Response<SearchResponse<Result>>

    @GET("lookup")
    suspend fun lookup(@Query("id") id:Int) :Response<SearchResponse<Result>>

    @GET("lookup")
    suspend fun lookupSoftware(@Query("id") id:Int) :Response<SearchResponse<AppResult>>

}