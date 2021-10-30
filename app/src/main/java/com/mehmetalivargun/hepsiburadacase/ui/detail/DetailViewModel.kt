package com.mehmetalivargun.hepsiburadacase.ui.detail

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.*
import com.mehmetalivargun.hepsiburadacase.data.model.AppResult
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.util.constants.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableLiveData<DetailState> = MutableLiveData()
    val state: LiveData<DetailState> = _state
    private var isPlaying: Boolean = false
    private val _softwareResult: MutableLiveData<AppResult> = MutableLiveData()
    val softwareResult: LiveData<AppResult> = _softwareResult
    private val _result: MutableLiveData<Result> = MutableLiveData()
    val result: LiveData<Result> = _result
    var mediaPlayer = MediaPlayer()
    val id: Int? = savedStateHandle["id"]
    val kind: String? = savedStateHandle["kind"]

    init {
        _state.value = DetailState.Loading
        initialData()

    }

    fun initialData() {
        when (kind) {
            "software" -> id?.let { lookupSoftware(it) }
            else -> id?.let { lookup(it) }
        }
    }

    fun playAudio(url: String) {
        if (isPlaying) {
            resumeAudio()
        } else {
            mediaPlayer.apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                try {
                    setDataSource(url)
                    prepareAsync()
                    mediaPlayer.setOnPreparedListener {
                        start()
                    }
                    this@DetailViewModel.isPlaying = true
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun releaseMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()

    }

    fun pauseAudio() = mediaPlayer.pause()

    fun resumeAudio()= mediaPlayer.start()

    private fun onLoading() {
        _state.value = DetailState.Loading
    }

    private fun onFail() {
        _state.value = DetailState.Failure
    }


    private fun lookup(id: Int) = viewModelScope.launch {
        repository.lookup(id).collect {
            Log.e("Result", "lookUp")
            when (it) {
                is DetailRepository.LookupResponseResult.Success.SuccessResult -> onSuccesResult(it.result)
                DetailRepository.LookupResponseResult.Failure -> onFail()
                DetailRepository.LookupResponseResult.Loading -> onLoading()

            }
        }
    }

    private fun lookupSoftware(id: Int) = viewModelScope.launch {
        repository.lookupSoftware(id).collect {
            when (it) {
                is DetailRepository.LookupResponseResult.Success.SuccessAppResult -> onSuccesAppResult(
                    it.appResult
                )
                DetailRepository.LookupResponseResult.Failure -> onFail()
                DetailRepository.LookupResponseResult.Loading -> onLoading()
            }
        }
    }

    private fun onSuccesAppResult(data: AppResult) {
        _softwareResult.value = data
        _state.value = DetailState.Succes
    }

    private fun onSuccesResult(data: Result) {
        _result.value = data
        _state.value = DetailState.Succes
    }


}
