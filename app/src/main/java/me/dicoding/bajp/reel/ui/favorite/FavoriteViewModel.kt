package me.dicoding.bajp.reel.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.data.model.entity.FavoriteEntity
import me.dicoding.bajp.reel.data.model.query.FavoriteQuery
import me.dicoding.bajp.reel.data.repository.FavoriteRepository
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Indexes
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.utils.DatabaseConstants.SORT_ASC

class FavoriteViewModel(
  private val repository: FavoriteRepository
) : ViewModel() {
  private val query: FavoriteQuery = FavoriteQuery(
    type = Types.TYPE_ALL,
    sortColumns = Indexes.TITLE,
    sortDirection = SORT_ASC,
    searchQuery = ""
  )

  private val _items = MutableLiveData<PagingData<FavoriteEntity>>()
  val items: LiveData<PagingData<FavoriteEntity>> get() = _items

  fun fetchFavoriteItems() {
    viewModelScope.launch {
      repository.getFavoriteItems(query, viewModelScope)
        .collect { result ->
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

  fun getSortCode() = query.sortColumns + query.sortDirection

  fun reOrderItems(
    column: Int,
    direction: Int
  ) {
    query.sortColumns = column
    query.sortDirection = direction
    fetchFavoriteItems()
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}