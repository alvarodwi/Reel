package me.dicoding.bajp.reel.data.model.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity

@Serializable
data class TvShowJson(
    @SerialName("id") val id: Long,
    @SerialName("poster_path") val poster: String? = null,
    @SerialName("backdrop_path") val backdrop: String? = null,
    @SerialName("first_air_date") val firstAirDate: String,

    @SerialName("name") val name: String,
    @SerialName("overview") val overview: String,
    @SerialName("genres") val genres: List<GenresJson> = emptyList(),
    @SerialName("homepage") val homepage: String? = null,
)

@Serializable
data class TvShowListJson(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<TvShowJson>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int,
)

fun TvShowJson.asEntity(): TvShowEntity {
    val listGenre: List<String> = genres.map { it.name }

    return TvShowEntity(
        id = id,
        poster = poster,
        backdrop = backdrop,
        firstAirDate = firstAirDate,
        name = name,
        overview = overview,
        genres = listGenre,
        homepage = homepage,
    )
}