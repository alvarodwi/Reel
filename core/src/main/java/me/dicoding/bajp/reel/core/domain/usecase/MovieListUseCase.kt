package me.dicoding.bajp.reel.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.Movie

interface MovieListUseCase {
  fun getPopularMovie(): Flow<NetworkResult<List<Movie>>>
}