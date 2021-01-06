package me.dicoding.bajp.reel.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.dicoding.bajp.reel.core.data.db.dao.FavoriteDao
import me.dicoding.bajp.reel.core.data.db.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}
