package me.dicoding.bajp.reel.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.Movie
import me.dicoding.bajp.reel.core.domain.usecase.MovieListUseCase

class MovieListViewModel(
    private val useCase: MovieListUseCase
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchPopularMovie() {
        viewModelScope.launch {
            _loading.postValue(true)
            _errorMessage.postValue("")
            useCase.getPopularMovie()
                .catch { _errorMessage.postValue(it.message) }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> _movies.postValue(result.data)
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
