package com.mehmetalivargun.hepsiburadacase.ui.search

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mehmetalivargun.hepsiburadacase.data.model.SearchItem
import com.mehmetalivargun.hepsiburadacase.data.paging.SearchResultPagingDataSource
import com.mehmetalivargun.hepsiburadacase.data.remote.ITunesService
import com.mehmetalivargun.hepsiburadacase.util.constants.Constants.NETWORK_PAGE_SIZE
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType
import javax.inject.Inject

class SearchRepository @Inject constructor(private var api : ITunesService) {
     fun search(term:String, entityType: EntityType): LiveData<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchResultPagingDataSource(api, term,entityType.value.enumValue)
            }
        ).liveData
    }
}