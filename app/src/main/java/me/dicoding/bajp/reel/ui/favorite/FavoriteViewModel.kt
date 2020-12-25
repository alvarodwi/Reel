package me.dicoding.bajp.reel.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.core.data.db.FavoriteQuery
import me.dicoding.bajp.reel.core.domain.model.Favorite
import me.dicoding.bajp.reel.core.domain.repository.FavoriteRepository
import me.dicoding.bajp.reel.core.domain.usecase.FavoriteListUseCase
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Sorts
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types

class FavoriteViewModel(
  private val useCase: FavoriteListUseCase
) : ViewModel() {
  private val query: FavoriteQuery = FavoriteQuery(
    type = Types.TYPE_ALL,
    sort = Sorts.TITLE_ASC,
    searchQuery = ""
  )

  private val _items = MutableLiveData<PagingData<Favorite>>()
  val items: LiveData<PagingData<Favorite>> get() = _items

  fun fetchFavoriteItems() {
    viewModelScope.launch {
      useCase.getFavoriteItems(query, viewModelScope).collect { result ->
        _items.postValue(result)
      }
    }
  }

  fun updateFilter(type: Int) {
    query.type = type
    fetchFavoriteItems()
  }

  fun searchItems(s: String) {
    query.searchQuery = s
    fetchFavoriteItems()
  }

  fun resetSearch() {
    query.searchQuery = ""
    fetchFavoriteItems()
  }

  fun getSortCode() = query.sort

  fun reOrderItems(
    sort: Int
  ) {
    query.sort = sort
    fetchFavoriteItems()
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}