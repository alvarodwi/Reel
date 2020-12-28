package me.dicoding.bajp.reel.core.data.interactor

import me.dicoding.bajp.reel.core.domain.repository.MovieRepository
import me.dicoding.bajp.reel.core.domain.usecase.MovieListUseCase

class MovieListInteractor(private val repository: MovieRepository) : MovieListUseCase {
  override fun getPopularMovie() = repository.getPopularMovie()
}