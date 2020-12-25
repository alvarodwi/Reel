package me.dicoding.bajp.reel.di

import me.dicoding.bajp.reel.core.data.interactor.FavoriteListInteractor
import me.dicoding.bajp.reel.core.data.interactor.MovieDetailInteractor
import me.dicoding.bajp.reel.core.data.interactor.MovieListInteractor
import me.dicoding.bajp.reel.core.data.interactor.TvShowDetailInteractor
import me.dicoding.bajp.reel.core.data.interactor.TvShowListInteractor
import me.dicoding.bajp.reel.core.domain.usecase.FavoriteListUseCase
import me.dicoding.bajp.reel.core.domain.usecase.MovieDetailUseCase
import me.dicoding.bajp.reel.core.domain.usecase.MovieListUseCase
import me.dicoding.bajp.reel.core.domain.usecase.TvShowDetailUseCase
import me.dicoding.bajp.reel.core.domain.usecase.TvShowListUseCase
import me.dicoding.bajp.reel.ui.favorite.FavoriteViewModel
import me.dicoding.bajp.reel.ui.movie.detail.MovieDetailViewModel
import me.dicoding.bajp.reel.ui.movie.list.MovieListViewModel
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailViewModel
import me.dicoding.bajp.reel.ui.tvshow.list.TvShowListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
  factory<MovieListUseCase> { MovieListInteractor(get()) }
  factory<MovieDetailUseCase> { MovieDetailInteractor(get()) }
  factory<TvShowListUseCase> { TvShowListInteractor(get()) }
  factory<TvShowDetailUseCase> { TvShowDetailInteractor(get()) }
  factory<FavoriteListUseCase> { FavoriteListInteractor(get()) }
}

val viewModelModule = module {
  viewModel { MovieListViewModel(get()) }
  viewModel { (id: Long) -> MovieDetailViewModel(id, get()) }

  viewModel { TvShowListViewModel(get()) }
  viewModel { (id: Long) -> TvShowDetailViewModel(id, get()) }

  viewModel { FavoriteViewModel(get()) }
}