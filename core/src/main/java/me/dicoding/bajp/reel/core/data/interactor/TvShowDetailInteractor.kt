package me.dicoding.bajp.reel.core.data.interactor

import me.dicoding.bajp.reel.core.domain.model.TvShow
import me.dicoding.bajp.reel.core.domain.repository.TvShowRepository
import me.dicoding.bajp.reel.core.domain.usecase.TvShowDetailUseCase

class TvShowDetailInteractor(private val repository: TvShowRepository) : TvShowDetailUseCase {
  override fun getTvShowDetailData(id: Long) = repository.getTvShowDetailData(id)

  override suspend fun addTvShowToFavorites(data: TvShow) =
    repository.addTvShowToFavorites(data)

  override suspend fun removeTvShowFromFavorites(data: TvShow) =
    repository.removeTvShowFromFavorites(data)

  override fun isTvShowInFavorites(id: Long) =
    repository.isTvShowInFavorites(id)
}