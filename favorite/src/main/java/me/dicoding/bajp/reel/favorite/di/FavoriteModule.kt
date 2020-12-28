package me.dicoding.bajp.reel.favorite.di

import me.dicoding.bajp.reel.favorite.ui.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
  viewModel { FavoriteViewModel(get()) }
}