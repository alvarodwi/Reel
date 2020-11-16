package me.dicoding.bajp.reel.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.MovieRepository

class MovieDetailViewModel(
  private val movieId: Long,
  private val repository: MovieRepository,
) : ViewModel() {
  private val _movie = MutableLiveData<MovieEntity>()
  val movie get() = _movie

  private val _errorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> get() = _errorMessage

  val isFavorited = repository.isMovieInFavorites(movieId).map { it == 1 }.asLiveData()

  fun fetchMovieDetail() {
    viewModelScope.launch {
      _errorMessage.postValue("")
      repository.getMovieDetailData(movieId)
        .catch { _errorMessage.postValue(it.message) }
        .collect { result ->
          when (result) {
            is NetworkResult.Success -> _movie.postValue(result.data)
            is NetworkResult.Error -> _errorMessage.postValue(result.exception.message)
          }
        }
    }
  }

  fun onFabClicked(data: MovieEntity) {
    viewModelScope.launch {
      if (isFavorited.value == true) repository.removeMovieFromFavorites(data)
      else repository.addMovieToFavorites(data)
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}