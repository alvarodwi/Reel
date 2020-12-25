package me.dicoding.bajp.reel.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.dicoding.bajp.reel.core.data.db.AppDatabase
import me.dicoding.bajp.reel.core.data.model.query.FavoriteQuery

class FavoriteRepository(
  private val db: AppDatabase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

  fun getFavoriteItems(
    query: FavoriteQuery,
    scope: CoroutineScope
  ) = Pager(
    PagingConfig(pageSize = 10)
  ) {
    db.favoriteDao.getItemsRaw(query.generateQuery()).asPagingSourceFactory(dispatcher).invoke()
  }.flow
    .cachedIn(scope)
}