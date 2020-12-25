package me.dicoding.bajp.reel.core

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.dicoding.bajp.reel.core.data.model.json.FavoriteJson
import me.dicoding.bajp.reel.core.data.model.json.MovieJson
import me.dicoding.bajp.reel.core.data.model.json.MovieListJson
import me.dicoding.bajp.reel.core.data.model.json.TvShowJson
import me.dicoding.bajp.reel.core.data.model.json.TvShowListJson

object JsonHelper {
  val jsonBuilder = Json {
    ignoreUnknownKeys = true
    isLenient = true
  }

  fun loadMovieData(jsonString: String): MovieJson =
    jsonBuilder.decodeFromString(jsonString)

  fun loadTvShowData(jsonString: String): TvShowJson =
    jsonBuilder.decodeFromString(jsonString)

  fun loadPopularMovieData(jsonString: String): MovieListJson =
    jsonBuilder.decodeFromString(jsonString)

  fun loadPopularTvShowData(jsonString: String): TvShowListJson =
    jsonBuilder.decodeFromString(jsonString)

  fun loadFavoritesData(jsonString: String): List<FavoriteJson> =
    jsonBuilder.decodeFromString(jsonString)
}