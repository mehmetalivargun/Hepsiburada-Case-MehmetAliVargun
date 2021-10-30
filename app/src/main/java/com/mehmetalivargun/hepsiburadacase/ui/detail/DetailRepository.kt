package com.mehmetalivargun.hepsiburadacase.ui.detail

import com.mehmetalivargun.hepsiburadacase.data.model.AppResult
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.remote.ITunesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepository @Inject constructor(val api: ITunesService) {

    suspend fun lookup(id: Int): Flow<Any> = flow {
        emit(LookupResponseResult.Loading)
        val response = try {
            api.lookup(id)

        } catch (ex: Exception) {
            null
        }
        when (response?.code()) {
            null -> emit(LookupResponseResult.Failure)
            200 -> {
                val results = response.body()?.results?.get(0)
                emit(LookupResponseResult.Success.SuccessResult(results!!))
            }
            else -> emit(LookupResponseResult.UnexpectedError)
        }
    }

    suspend fun lookupSoftware(id: Int): Flow<Any> = flow {
        emit(LookupResponseResult.Loading)
        val response = try {
            api.lookupSoftware(id)

        } catch (ex: Exception) {
            null
        }
        when (response?.code()) {
            null -> emit(LookupResponseResult.Failure)
            200 -> {
                val results = response.body()?.results?.get(0)
                emit(LookupResponseResult.Success.SuccessAppResult(results!!))
            }
            else -> emit(LookupResponseResult.Failure)
        }
    }

    sealed class LookupResponseResult {
        sealed class Success<out T>(val response: T?) : LookupResponseResult() {
            data class SuccessResult(val result: Result) : Success<Result>(result)
            data class SuccessAppResult(val appResult: AppResult) : Success<AppResult>(appResult)
        }

        object Failure : LookupResponseResult()
        object UnexpectedError : LookupResponseResult()
        object Loading : LookupResponseResult()
    }

}