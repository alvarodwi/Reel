package me.dicoding.bajp.reel.core.domain.repository

import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.TvShow

interface TvShowRepository {
    fun getPopularTvShow(): Flow<NetworkResult<List<TvShow>>>

    fun getTvShowDetailData(id: Long): Flow<NetworkResult<TvShow>>

    suspend fun addTvShowToFavorites(data: TvShow): Long

    suspend fun removeTvShowFromFavorites(data: TvShow): Int

    fun isTvShowInFavorites(id: Long): Flow<Int>
}
