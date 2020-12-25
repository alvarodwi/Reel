package me.dicoding.bajp.reel.core.data.network.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListJson(
  @SerialName("page") val page: Int,
  @SerialName("results") val results: List<MovieJson>,
  @SerialName("total_pages") val totalPages: Int,
  @SerialName("total_results") val totalResults: Int,
)