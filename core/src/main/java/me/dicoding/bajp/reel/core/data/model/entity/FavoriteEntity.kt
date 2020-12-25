package me.dicoding.bajp.reel.core.data.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import me.dicoding.bajp.reel.core.utils.DatabaseConstants as DBConst

@Entity(
  tableName = "favorites",
  indices = [Index(value = ["tmdb_id"])]
)
data class FavoriteEntity(
  @PrimaryKey(autoGenerate = true) @NonNull val uid: Long = 0L,
  @ColumnInfo(name = "tmdb_id") val tmdbId: Long,
  @ColumnInfo(name = "item_title") val itemTitle: String,
  @ColumnInfo(name = "item_poster_url") val itemPosterUrl: String,
  @ColumnInfo(name = "item_date") val itemDate: String = "",
  @ColumnInfo(name = "date_added") val dateAdded: String = "",
  val type: Int
)

fun MovieEntity.asFavoriteEntity() = FavoriteEntity(
  tmdbId = this.id,
  itemTitle = this.title,
  itemPosterUrl = this.posterUrl,
  itemDate = this.releaseDate,
  dateAdded = LocalDateTime.now().toString(),
  type = DBConst.FavoriteTable.Types.TYPE_MOVIE
)

fun TvShowEntity.asFavoriteEntity() = FavoriteEntity(
  tmdbId = this.id,
  itemTitle = this.name,
  itemPosterUrl = this.posterUrl,
  itemDate = this.firstAirDate,
  dateAdded = LocalDateTime.now().toString(),
  type = DBConst.FavoriteTable.Types.TYPE_TV_SHOW
)