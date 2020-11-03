package me.dicoding.bajp.reel.ui.tvshow.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.TvShowRepository

class TvShowDetailViewModel(
    private val tvShowId: Long,
    private val repository: TvShowRepository
) : ViewModel() {
    private val _tvShow = MutableLiveData<TvShowEntity>()
    val tvShow get() = _tvShow

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchTvShowDetail(){
        viewModelScope.launch {
            _errorMessage.postValue("")
            repository.getTvShowDetailData(tvShowId)
                .catch { _errorMessage.postValue(it.message) }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> _tvShow.postValue(result.data)
                        is NetworkResult.Error -> _errorMessage.postValue(result.exception.message)
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}