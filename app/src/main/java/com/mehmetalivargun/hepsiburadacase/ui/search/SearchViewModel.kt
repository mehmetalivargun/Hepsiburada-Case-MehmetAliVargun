package com.mehmetalivargun.hepsiburadacase.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetalivargun.hepsiburadacase.data.model.Entity
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.remote.ApiDataSource
import com.mehmetalivargun.hepsiburadacase.data.remote.ApiDataSource.SearchResult.*
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private var api : ApiDataSource) : ViewModel() {
     fun search(term:String,entity:String) = viewModelScope.launch {
            api.search(term,EntityType.MUSIC.value.enumValue).collect {
                when(it){
                    is Success -> onSucces(it.searchResults)
                    UnexpectedError -> onUnexpectedError()
                    Failure -> onFailure()
                    Loading -> onLoading()

                }
            }
    }

    private fun onLoading() {
       Log.e("Test","onLoading")
    }

    private fun onFailure() {
        Log.e("Test","onFailure")
    }

    private fun onUnexpectedError() {
        Log.e("Test","onUnexpectedError")
    }

    private fun onSucces(searchResults: List<Result>) {
        Log.e("Test",searchResults.size.toString())

    }
}