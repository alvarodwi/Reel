package me.dicoding.bajp.reel.ui.tvshow.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.core.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.repository.TvShowRepository

class TvShowListViewModel(
  private val repository: TvShowRepository
) : ViewModel() {
  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> get() = _loading

  private val _tvShows = MutableLiveData<List<TvShowEntity>>()
  val tvShows: LiveData<List<TvShowEntity>> get() = _tvShows

  private val _errorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> get() = _errorMessage

  fun fetchPopularTvShow() {
    viewModelScope.launch {
      _loading.postValue(true)
      _errorMessage.postValue("")
      repository.getPopularTvShow()
        .catch { _errorMessage.postValue(it.message) }
        .collect { result ->
          when (result) {
            is NetworkResult.Success -> _tvShows.postValue(result.data)
            is NetworkResult.Error -> _errorMessage.postValue(result.exception.message)
          }
        }
      _loading.postValue(false)
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}