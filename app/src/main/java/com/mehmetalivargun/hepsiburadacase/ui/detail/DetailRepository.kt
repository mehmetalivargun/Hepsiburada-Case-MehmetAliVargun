package com.mehmetalivargun.hepsiburadacase.ui.detail

import android.util.Log
import com.mehmetalivargun.hepsiburadacase.data.model.AppResult
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.remote.ITunesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class DetailRepository @Inject constructor(val api:ITunesService)  {

    suspend fun  lookup(id :Int): Flow<Any> = flow {
        Log.e("Result","repo")
        emit(LookupResponseResult.Loading)
        val response = try {
            api.lookup(id)

        }catch (ex: Exception){
            null
        }
        when(response?.code()){
            null->emit(LookupResponseResult.Failure)
            200->{
                val results= response.body()?.results?.get(0)
                emit(LookupResponseResult.Success(results!!))
            }
            else->emit(LookupResponseResult.UnexpectedError)
        }
    }

    suspend fun lookupSoftware(id:Int):Flow<Any> = flow{
        Log.e("Result","repo")
        emit(LookupSoftwareResponseResult.Loading)
        val response = try {
            api.lookupSoftware(id)

        }catch (ex: Exception){
            null
        }
        when(response?.code()){
            null->emit(LookupSoftwareResponseResult.Failure)
            200->{
                val results= response.body()?.results?.get(0)
                emit(LookupSoftwareResponseResult.Success(results!!))
            }
            else->emit(LookupSoftwareResponseResult.Failure)
        }
    }

/*
    sealed class LookupResponseResult {
        sealed class Success<out T>(val response: T?) : LookupResponseResult(){
             data class SuccessResult(val result:Result):Success<Result>(result)
             data class SuccessAppResult(val appResult:AppResult):Success<AppResult>(appResult)
        }
        object Failure : LookupResponseResult()
        object UnexpectedError : LookupResponseResult()
        object Loading : LookupResponseResult()
    }*/

    sealed class LookupResponseResult{
        class Success(val data: Result) : LookupResponseResult()
        object UnexpectedError : LookupResponseResult()
        object Loading : LookupResponseResult()
        object  Failure : LookupResponseResult()
    }

    sealed class LookupSoftwareResponseResult{
        class Success(val data: AppResult) : LookupSoftwareResponseResult()
        object UnexpectedError : LookupSoftwareResponseResult()
        object Loading : LookupSoftwareResponseResult()
        object  Failure : LookupSoftwareResponseResult()
    }



}