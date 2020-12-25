package me.dicoding.bajp.reel.core.data.network.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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