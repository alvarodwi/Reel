package me.dicoding.bajp.reel.utils

import android.content.Context
import kotlinx.serialization.json.Json
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.data.model.json.*
import java.util.*

class JsonHelper(private val context: Context) {
    private fun parsingRawResourceToString(id: Int): String? {
        val resourceReader = context.resources.openRawResource(id)
        return Scanner(resourceReader).useDelimiter("\\A").next()
    }

    fun loadMovieData(): MovieEntity {
        val jsonString = parsingRawResourceToString(R.raw.latest_movie) ?: ""
        return Json.decodeFromString(MovieJson.serializer(), jsonString).asEntity()
    }

    fun loadTvShowData(): TvShowEntity {
        val jsonString = parsingRawResourceToString(R.raw.latest_tv_show) ?: ""
        return Json.decodeFromString(TvShowJson.serializer(), jsonString).asEntity()
    }

    fun loadPopularMovieData(): List<MovieEntity> {
        val jsonString = parsingRawResourceToString(R.raw.popular_movies) ?: ""
        return Json.decodeFromString(
            MovieListJson.serializer(),
            jsonString
        ).results.map(MovieJson::asEntity)
    }

    fun loadPopularTvShowData(): List<TvShowEntity> {
        val jsonString = parsingRawResourceToString(R.raw.popular_tv_shows) ?: ""
        return Json.decodeFromString(
            TvShowListJson.serializer(),
            jsonString
        ).results.map(TvShowJson::asEntity)
    }
}