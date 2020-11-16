package me.dicoding.bajp.reel.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.dicoding.bajp.reel.data.db.dao.FavoriteDao
import me.dicoding.bajp.reel.data.model.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract val favoriteDao: FavoriteDao
}