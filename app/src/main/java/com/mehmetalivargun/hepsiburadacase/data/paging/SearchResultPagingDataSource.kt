package com.mehmetalivargun.hepsiburadacase.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.model.SearchResultItemResponse
import com.mehmetalivargun.hepsiburadacase.data.model.mapper.SearchResultMapper
import com.mehmetalivargun.hepsiburadacase.data.remote.ITunesService
import java.lang.Exception

const val NETWORK_PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 1
class SearchResultPagingDataSource(private val service : ITunesService, private  val mapper: SearchResultMapper , private val term:String,private val entity:String) : PagingSource<Int,SearchResultItemResponse>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultItemResponse> {
        // using offset and limit parameters for pagination
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null) ((position-1 ) * NETWORK_PAGE_SIZE)+1 else INITIAL_LOAD_SIZE

        return try {

            val jsonResponse = service.search(term = term,entity = entity,offset = offset,limit = params.loadSize).body()?.results

            val mappedRespons = mapper.toSearchResponseList(jsonResponse)
            val next= if(mappedRespons.isEmpty()){
                null
            }else{
                // 3* PageSize initially loaded so dont load same data again
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            Log.e("offsetNext",next.toString())
            LoadResult.Page(
                data = mappedRespons,
                prevKey = null,
                nextKey =next
            )
        }catch (e : Exception){
                LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchResultItemResponse>): Int?{
        return null
    }
}