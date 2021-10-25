package com.mehmetalivargun.hepsiburadacase.data.remote

import com.mehmetalivargun.hepsiburadacase.data.remote.ApiDataSource.SearchResult.*
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val api:ITunesService) {
    suspend fun  search(query:String,entity:String) = flow<Any>{
      emit(Loading)
       val response=try {
           api.search(query,entity)
       }catch (ex: Exception){
           null
       }
        when(response?.code()){
            null-> emit(Failure)
            200->{
                val searchResult = response.body()!!.results
                emit(Success(searchResult))
            }
            else->emit(UnexpectedError)
        }
    }
    sealed class SearchResult {
        class Success(val searchResults: List<Result>) : SearchResult()
        object Failure : SearchResult()
        object UnexpectedError : SearchResult()
        object Loading : SearchResult()
    }

}