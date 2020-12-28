package me.dicoding.bajp.reel.core.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.db.FavoriteQuery
import me.dicoding.bajp.reel.core.domain.model.Favorite

interface FavoriteRepository {
  fun getFavoriteItems(
    query: FavoriteQuery,
    scope: CoroutineScope
  ): Flow<PagingData<Favorite>>
}