package me.dicoding.bajp.reel.core.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import me.dicoding.bajp.reel.core.data.db.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @RawQuery(observedEntities = [FavoriteEntity::class])
    suspend fun checkItemsRaw(query: SupportSQLiteQuery): Int

    @RawQuery(observedEntities = [FavoriteEntity::class])
    fun getItemsRaw(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity>

    // https://medium.com/androiddevelopers/room-flow-273acffe5b57
    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE tmdb_id = :id AND type = :type)")
    fun isItemWithIdExists(
        id: Long,
        type: Int
    ): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(data: FavoriteEntity): Long

    @Query("DELETE FROM favorites WHERE tmdb_id = :id AND type = :type")
    suspend fun deleteItem(
        id: Long,
        type: Int
    ): Int
}
