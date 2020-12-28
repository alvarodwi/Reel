package me.dicoding.bajp.reel.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.TvShow

interface TvShowListUseCase {
  fun getPopularTvShow(): Flow<NetworkResult<List<TvShow>>>
}