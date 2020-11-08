package me.dicoding.bajp.reel.data.repository

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SimpleSQLiteQuery
import me.dicoding.bajp.reel.data.db.AppDatabase
import me.dicoding.bajp.reel.data.db.dao.FavoriteDao
import me.dicoding.bajp.reel.data.model.entity.FavoriteEntity
import me.dicoding.bajp.reel.data.model.usecase.FavoriteUseCase
import me.dicoding.bajp.reel.utils.DatabaseConstants as DbConst

class FavoriteRepository(
    private val db: AppDatabase
) {
    private val favDao: FavoriteDao = db.favoriteDao
    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(4)
        .setPageSize(5)
        .build()

    private fun generateQuery(
        useCase: FavoriteUseCase
    ): SimpleSQLiteQuery {
        val result = StringBuilder().append("SELECT * FROM favorites")
        when (useCase.type) {
            DbConst.Type.TYPE_MOVIE -> result.append(" WHERE type = 1")
            DbConst.Type.TYPE_TV_SHOW -> result.append(" WHERE type = 2")
        }
        if (useCase.searchQuery.isNotBlank()) {
            result.append(" AND WHERE item_title LIKE %${useCase.searchQuery}%")
        }
        when (useCase.sort) {
            DbConst.Sort.TITLE_ASC -> result.append("ORDER BY item_title ASC")
            DbConst.Sort.TITLE_DESC -> result.append("ORDER BY item_title DESC")
            DbConst.Sort.DATE_ASC -> result.append("ORDER BY item_date ASC")
            DbConst.Sort.DATE_DESC -> result.append("ORDER BY item_date DESC")
        }
        if (useCase.isGrouped) result.append(" GROUP BY type")
        return SimpleSQLiteQuery(result.toString())
    }

    fun fetchFavoriteItems(useCase: FavoriteUseCase): LivePagedListBuilder<Int, FavoriteEntity> =
        LivePagedListBuilder(favDao.getItemsRaw(generateQuery(useCase)),pagedListConfig)
}