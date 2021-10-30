package com.mehmetalivargun.hepsiburadacase.ui.detail

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mehmetalivargun.hepsiburadacase.data.model.AppResult
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.model.SearchSoftwareItem
import com.mehmetalivargun.hepsiburadacase.data.model.SearchTrackItem
import com.mehmetalivargun.hepsiburadacase.ui.search.SearchRepository
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _softwareResult: MutableLiveData<AppResult> = MutableLiveData()
    val softwareResult: LiveData<AppResult> = _softwareResult
    private val _result: MutableLiveData<Result> = MutableLiveData()
    val result: LiveData<Result> = _result

    init {
        val id: Int? = savedStateHandle["id"]
        val kind:String? =savedStateHandle["kind"]

        when (kind) {
            "software" -> id?.let { lookupSoftware(it) }
            else -> id?.let { lookup(it) }
        }
    }

    private fun lookup(id: Int) = viewModelScope.launch {
        repository.lookup(id).collect {
            Log.e("Result","lookUp")
            when (it) {
                is DetailRepository.LookupResponseResult.Success -> onSuccesResult(it.data)
                DetailRepository.LookupResponseResult.Failure -> onFail()
                DetailRepository.LookupResponseResult.Loading -> onLoading()

            }
        }
    }




    private fun onLoading() {
        _isLoading.value = true
    }

    private fun onFail() {
        _isLoading.value = false
    }


    private fun lookupSoftware(id: Int) = viewModelScope.launch {
        repository.lookupSoftware(id).collect {
            when (it) {
                is DetailRepository.LookupSoftwareResponseResult.Success ->onSuccesAppResult(it.data)
                DetailRepository.LookupSoftwareResponseResult.Failure -> onFail()
                DetailRepository.LookupSoftwareResponseResult.Loading -> onLoading()
            }
        }
    }

    private fun onSuccesAppResult(data : AppResult){
          _softwareResult.value=data
        _isLoading.value = false
    }
    private fun onSuccesResult(data : Result){
        _result.value=data
        _isLoading.value = false
    }





}
