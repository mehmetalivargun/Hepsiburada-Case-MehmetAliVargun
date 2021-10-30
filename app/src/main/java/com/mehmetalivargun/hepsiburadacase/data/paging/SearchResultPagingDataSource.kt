package com.mehmetalivargun.hepsiburadacase.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mehmetalivargun.hepsiburadacase.data.model.SearchItem
import com.mehmetalivargun.hepsiburadacase.data.model.mapper.toSearchResultItemResponse
import com.mehmetalivargun.hepsiburadacase.data.remote.ITunesService
import com.mehmetalivargun.hepsiburadacase.util.constants.Constants.INITIAL_LOAD_SIZE
import com.mehmetalivargun.hepsiburadacase.util.constants.Constants.NETWORK_PAGE_SIZE
import java.lang.Exception


class SearchResultPagingDataSource(private val service : ITunesService , private val term:String, private val entity:String) : PagingSource<Int,SearchItem>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        // using offset and limit parameters for pagination
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null) ((position-1 ) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE

        return try {
            val jsonResponse = service.search(term = term,entity = entity,offset = offset,limit = params.loadSize).body()?.results
            val mappedResponse = jsonResponse.toSearchResultItemResponse()
            val next= if(mappedResponse?.isEmpty()!!){
                null
            }else{
                // 3* PageSize initially loaded so dont load same data again
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            Log.e("offsetNext",next.toString())
            LoadResult.Page(
                data = mappedResponse,
                prevKey = null,
                nextKey =next
            )
        }catch (e : Exception){
                LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int?{
        return null
    }
}