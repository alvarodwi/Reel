package me.dicoding.bajp.reel.core.data.model.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.dicoding.bajp.reel.core.data.model.entity.FavoriteEntity

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

fun FavoriteJson.asEntity(): FavoriteEntity = FavoriteEntity(
  uid = this.uid,
  tmdbId = this.tmdbId,
  itemTitle = this.itemTitle,
  itemDate = this.itemDate,
  itemPosterUrl = this.itemPosterUrl,
  dateAdded = this.dateAdded,
  type = this.type
)