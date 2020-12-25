package me.dicoding.bajp.reel.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.db.AppDatabase
import me.dicoding.bajp.reel.core.data.db.FavoriteQuery
import me.dicoding.bajp.reel.core.data.db.entity.FavoriteEntity
import me.dicoding.bajp.reel.core.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
  private val db: AppDatabase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavoriteRepository {

  override fun getFavoriteItems(
    query: FavoriteQuery,
    scope: CoroutineScope
  ): Flow<PagingData<FavoriteEntity>> = Pager(
    PagingConfig(pageSize = 10)
  ) {
    db.favoriteDao.getItemsRaw(query.generateQuery()).asPagingSourceFactory(dispatcher).invoke()
  }.flow
    .cachedIn(scope)
}