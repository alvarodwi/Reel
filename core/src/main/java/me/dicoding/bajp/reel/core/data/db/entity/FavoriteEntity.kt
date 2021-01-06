package me.dicoding.bajp.reel.core.data.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
