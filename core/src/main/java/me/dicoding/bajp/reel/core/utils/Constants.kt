package me.dicoding.bajp.reel.core.utils

import me.dicoding.bajp.reel.core.BuildConfig

const val API_KEY = BuildConfig.tmdbApiKey
const val BASE_URL = "https://api.themoviedb.org/3/"

object DatabaseConstants {
    object FavoriteTable {
        object Types {
            const val TYPE_ALL = 0
            const val TYPE_MOVIE = 1
            const val TYPE_TV_SHOW = 2
        }

        object Sorts {
            const val TITLE_ASC = 0
            const val TITLE_DESC = 1
            const val DATE_ASC = 2
            const val DATE_DESC = 3
            const val DATE_ADDED_ASC = 4
            const val DATE_ADDED_DESC = 5
        }
    }
}
