package me.dicoding.bajp.reel.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.TvShow

interface TvShowDetailUseCase {
    fun getTvShowDetailData(id: Long): Flow<NetworkResult<TvShow>>

    suspend fun addTvShowToFavorites(data: TvShow): Long

    suspend fun removeTvShowFromFavorites(data: TvShow): Int

    fun isTvShowInFavorites(id: Long): Flow<Int>
}
