package me.dicoding.bajp.reel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.dicoding.bajp.reel.data.db.AppDatabase
import me.dicoding.bajp.reel.data.db.dao.FavoriteDao
import me.dicoding.bajp.reel.data.model.query.FavoriteQuery

class FavoriteRepository(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
  private val dao: FavoriteDao = db.favoriteDao

  fun fetchFavoriteItems(query: FavoriteQuery) = Pager(
    PagingConfig(pageSize = 10)
  ) {
    dao.getItemsRaw(query.generateQuery())
      .asPagingSourceFactory()
      .invoke()
  }.flow
}