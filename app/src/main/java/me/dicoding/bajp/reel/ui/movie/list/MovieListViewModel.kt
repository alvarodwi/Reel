package me.dicoding.bajp.reel.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.MovieRepository

class MovieListViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _movies = MutableLiveData<List<MovieEntity>>()
    val movies: LiveData<List<MovieEntity>> get() = _movies
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        _errorMessage.postValue("")
        fetchPopularMovie()
    }

    fun fetchPopularMovie(){
        viewModelScope.launch {
            _loading.postValue(true)
            repository.getPopularMovie()
                .catch { _errorMessage.postValue(it.message) }
                .collect { result ->
                    when(result){
                        is NetworkResult.Success -> {
                            _movies.postValue(result.data)
                            _errorMessage.postValue("")
                        }
                        is NetworkResult.Error -> _errorMessage.postValue(result.message)
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