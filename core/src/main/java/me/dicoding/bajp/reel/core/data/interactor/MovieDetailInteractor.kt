package me.dicoding.bajp.reel.core.data.interactor

import me.dicoding.bajp.reel.core.domain.model.Movie
import me.dicoding.bajp.reel.core.domain.repository.MovieRepository
import me.dicoding.bajp.reel.core.domain.usecase.MovieDetailUseCase

class MovieDetailInteractor(private val repository: MovieRepository) : MovieDetailUseCase {
  override fun getMovieDetailData(id: Long) = repository.getMovieDetailData(id)

  override suspend fun addMovieToFavorites(data: Movie) =
    repository.addMovieToFavorites(data)

  override suspend fun removeMovieFromFavorites(data: Movie) =
    repository.removeMovieFromFavorites(data)

  override fun isMovieInFavorites(id: Long) =
    repository.isMovieInFavorites(id)
}