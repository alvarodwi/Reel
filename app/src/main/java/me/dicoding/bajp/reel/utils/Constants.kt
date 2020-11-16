package me.dicoding.bajp.reel.utils

import me.dicoding.bajp.reel.BuildConfig

const val API_KEY = BuildConfig.tmdbApiKey
const val BASE_URL = "https://api.themoviedb.org/3/"

object DatabaseConstants {
  const val SORT_ASC = 0
  const val SORT_DESC = 1

  object FavoriteTable {
    object Types {
      const val TYPE_ALL = 0
      const val TYPE_MOVIE = 1
      const val TYPE_TV_SHOW = 2
    }

    object Indexes {
      const val TITLE = 0
      const val DATE = 1
      const val DATE_ADDED = 2
    }
  }
}