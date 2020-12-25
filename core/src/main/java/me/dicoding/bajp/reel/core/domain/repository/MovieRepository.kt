package me.dicoding.bajp.reel.core.domain.repository

import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.Movie

interface MovieRepository {
  fun getPopularMovie(): Flow<NetworkResult<List<Movie>>>

  fun getMovieDetailData(id: Long): Flow<NetworkResult<Movie>>

  suspend fun addMovieToFavorites(data: Movie): Long
  suspend fun removeMovieFromFavorites(data: Movie): Int
  fun isMovieInFavorites(id: Long): Flow<Int>
}