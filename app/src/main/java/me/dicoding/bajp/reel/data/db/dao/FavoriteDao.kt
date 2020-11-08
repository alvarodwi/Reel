package me.dicoding.bajp.reel.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import me.dicoding.bajp.reel.data.model.entity.FavoriteEntity

@Dao
interface FavoriteDao{
    @RawQuery(observedEntities = [FavoriteEntity::class])
    fun getItemsRaw(query : SupportSQLiteQuery) : DataSource.Factory<Int,FavoriteEntity>
}