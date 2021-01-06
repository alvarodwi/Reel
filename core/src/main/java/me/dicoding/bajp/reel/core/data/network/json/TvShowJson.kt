package me.dicoding.bajp.reel.core.data.network.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
