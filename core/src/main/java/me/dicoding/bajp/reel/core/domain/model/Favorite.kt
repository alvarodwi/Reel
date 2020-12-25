package me.dicoding.bajp.reel.core.domain.model

data class Favorite(
  val uid : Long = 0L,
  val tmdbId: Long,
  val itemTitle: String,
  val itemPosterUrl: String,
  val itemDate: String = "",
  val dateAdded: String = "",
  val type: Int
)
