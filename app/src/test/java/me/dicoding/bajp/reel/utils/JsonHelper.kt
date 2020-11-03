package me.dicoding.bajp.reel.utils

import kotlinx.serialization.json.Json
import me.dicoding.bajp.reel.data.model.json.MovieJson
import me.dicoding.bajp.reel.data.model.json.MovieListJson
import me.dicoding.bajp.reel.data.model.json.TvShowJson
import me.dicoding.bajp.reel.data.model.json.TvShowListJson

object JsonHelper {
    val jsonBuilder = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun loadMovieData(jsonString: String): MovieJson =
        jsonBuilder.decodeFromString(MovieJson.serializer(), jsonString)

    fun loadTvShowData(jsonString: String): TvShowJson =
        jsonBuilder.decodeFromString(TvShowJson.serializer(), jsonString)

    fun loadPopularMovieData(jsonString: String): MovieListJson =
        jsonBuilder.decodeFromString(
            MovieListJson.serializer(),
            jsonString
        )

    fun loadPopularTvShowData(jsonString: String): TvShowListJson =
        jsonBuilder.decodeFromString(
            TvShowListJson.serializer(),
            jsonString
        )
}