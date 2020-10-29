package me.dicoding.bajp.reel.data.model.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.dicoding.bajp.reel.data.model.entity.MovieEntity

@Serializable
data class MovieJson(
    @SerialName("id") val id: Long,
    @SerialName("poster_path") val poster: String? = null,
    @SerialName("backdrop_path") val backdrop: String? = null,
    @SerialName("release_date") val releaseDate: String,

    @SerialName("title") val title: String,
    @SerialName("tagline") val tagLine: String? = null,
    @SerialName("overview") val overview: String,
    @SerialName("genres") val genres: List<GenresJson> = emptyList(),
    @SerialName("homepage") val homepage: String? = null,
)

@Serializable
data class MovieListJson(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieJson>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int,
)

fun MovieJson.asEntity(): MovieEntity {
    val listGenre: List<String> = genres.map { it.name }

    return MovieEntity(
        id = id,
        poster = poster,
        backdrop = backdrop,
        releaseDate = releaseDate,
        title = title,
        overview = overview,
        tagLine = tagLine ?: "",
        genres = listGenre,
        homepage = homepage,
    )
}