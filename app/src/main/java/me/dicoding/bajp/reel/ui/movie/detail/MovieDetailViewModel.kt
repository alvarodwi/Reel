package me.dicoding.bajp.reel.ui.movie.detail

import androidx.lifecycle.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.MovieRepository

class MovieDetailViewModel(
    private val movieId: Long,
    private val repository: MovieRepository
) : ViewModel() {
    val movie = liveData {
        _errorMessage.postValue("")
        repository.getMovieDetailData(movieId)
            .catch { _errorMessage.postValue(it.message) }
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> emit(result.data)
                    is NetworkResult.Error -> _errorMessage.postValue(result.exception.message)
                }
            }
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}