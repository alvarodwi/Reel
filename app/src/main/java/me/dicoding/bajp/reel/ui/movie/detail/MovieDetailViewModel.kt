package me.dicoding.bajp.reel.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.core.domain.model.Movie
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.MovieRepository

class MovieDetailViewModel(
  private val movieId: Long,
  private val repository: MovieRepository,
) : ViewModel() {
  private val _movie = MutableLiveData<Movie>()
  val movie get() = _movie

  private val _errorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> get() = _errorMessage

  private val _isFavorite = MutableLiveData<Boolean>()
  val isFavorite get() = _isFavorite

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

  fun checkMovieInDb() {
    viewModelScope.launch {
      repository.isMovieInFavorites(movieId).collect { result ->
        _isFavorite.value = result == 1
      }
    }
  }

  fun onFabClicked(data: Movie) {
    viewModelScope.launch {
      if (isFavorite.value == true) repository.removeMovieFromFavorites(data)
      else repository.addMovieToFavorites(data)
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}