package me.dicoding.bajp.reel.ui.tvshow.detail

import androidx.lifecycle.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.TvShowRepository

class TvShowDetailViewModel(
    private val tvShowId: Long,
    private val repository: TvShowRepository
) : ViewModel() {
    val tvShow = liveData {
        repository.getTvShowDetailData(tvShowId)
            .catch { _errorMessage.postValue(it.message) }
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        emit(result.data)
                        _errorMessage.postValue("")
                    }
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