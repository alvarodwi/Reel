package me.dicoding.bajp.reel.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import coil.ImageLoader
import me.dicoding.bajp.reel.ui.favorite.FavoriteViewModel
import me.dicoding.bajp.reel.ui.movie.detail.MovieDetailViewModel
import me.dicoding.bajp.reel.ui.movie.list.MovieListViewModel
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailViewModel
import me.dicoding.bajp.reel.ui.tvshow.list.TvShowListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { (id: Long) -> MovieDetailViewModel(id, get()) }

    viewModel { TvShowListViewModel(get()) }
    viewModel { (id: Long) -> TvShowDetailViewModel(id, get()) }

    viewModel { FavoriteViewModel(get()) }
}