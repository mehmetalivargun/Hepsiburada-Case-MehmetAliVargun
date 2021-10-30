package com.mehmetalivargun.hepsiburadacase.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mehmetalivargun.hepsiburadacase.data.model.SearchItem
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository ) : ViewModel() {
    private val _searchResults = MutableLiveData<PagingData<SearchItem>>()

     fun search(entityType: EntityType,term:String): LiveData<PagingData<SearchItem>> {
        val result = repository.search(term,entityType).cachedIn(viewModelScope)
        _searchResults.value = result.value
        return result
    }




}