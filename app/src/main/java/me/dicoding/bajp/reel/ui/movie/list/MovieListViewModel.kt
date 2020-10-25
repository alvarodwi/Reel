package me.dicoding.bajp.reel.ui.movie.list

import androidx.lifecycle.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.MovieRepository

class MovieListViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    val movies = liveData<List<MovieEntity>> {
        repository.getPopularMovie()
            .catch { _errorMessage.postValue(it.message) }
            .collect { result ->
                when(result){
                    is NetworkResult.Success -> emit(result.data)
                    is NetworkResult.Error -> _errorMessage.postValue(result.message)
                }
            }
    }
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        _errorMessage.postValue("")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}