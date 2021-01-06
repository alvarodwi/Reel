package me.dicoding.bajp.reel.core.data.interactor

import me.dicoding.bajp.reel.core.domain.repository.TvShowRepository
import me.dicoding.bajp.reel.core.domain.usecase.TvShowListUseCase

class TvShowListInteractor(private val repository: TvShowRepository) : TvShowListUseCase {
    override fun getPopularTvShow() = repository.getPopularTvShow()
}
