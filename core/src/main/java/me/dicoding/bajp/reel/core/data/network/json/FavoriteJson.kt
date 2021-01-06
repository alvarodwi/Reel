package me.dicoding.bajp.reel.core.data.network.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// used for test purpose
@Serializable
data class FavoriteJson(
    @SerialName("id") val uid: Long = 0L,
    @SerialName("tmdb_id") val tmdbId: Long,
    @SerialName("item_title") val itemTitle: String,
    @SerialName("item_poster_url") val itemPosterUrl: String,
    @SerialName("item_date") val itemDate: String = "",
    @SerialName("date_added") val dateAdded: String = "",
    @SerialName("type") val type: Int
)
