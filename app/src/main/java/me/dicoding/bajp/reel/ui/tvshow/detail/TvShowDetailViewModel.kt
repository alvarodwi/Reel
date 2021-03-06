package me.dicoding.bajp.reel.ui.tvshow.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.TvShow
import me.dicoding.bajp.reel.core.domain.usecase.TvShowDetailUseCase

class TvShowDetailViewModel(
    private val tvShowId: Long,
    private val useCase: TvShowDetailUseCase
) : ViewModel() {
    private val _tvShow = MutableLiveData<TvShow>()
    val tvShow get() = _tvShow

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite get() = _isFavorite

    fun fetchTvShowDetail() {
        viewModelScope.launch {
            _errorMessage.postValue("")
            useCase.getTvShowDetailData(tvShowId)
                .catch { _errorMessage.postValue(it.message) }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> _tvShow.postValue(result.data)
                        is NetworkResult.Error -> _errorMessage.postValue(result.exception.message)
                    }
                }
        }
    }

    fun checkTvShowInDb() {
        viewModelScope.launch {
            useCase.isTvShowInFavorites(tvShowId).collect { result ->
                _isFavorite.value = result == 1
            }
        }
    }

    fun onFabClicked(data: TvShow) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite.value == true) {
                useCase.removeTvShowFromFavorites(data)
            } else useCase.addTvShowToFavorites(data)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
