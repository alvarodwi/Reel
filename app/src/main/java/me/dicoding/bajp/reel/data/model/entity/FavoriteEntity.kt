package me.dicoding.bajp.reel.data.model.entity

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
    @PrimaryKey @NonNull val uid: Long,
    @ColumnInfo(name = "tmdb_id") val tmdbId: Long,
    @ColumnInfo(name = "item_title") val itemTitle: String,
    @ColumnInfo(name = "item_date") val itemDate: String = "",
    val type: Int
)