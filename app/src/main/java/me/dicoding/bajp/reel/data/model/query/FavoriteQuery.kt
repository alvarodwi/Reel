package me.dicoding.bajp.reel.data.model.query

import androidx.sqlite.db.SimpleSQLiteQuery
import me.dicoding.bajp.reel.utils.DatabaseConstants as DBConst

data class FavoriteQuery(
  val type: Int = DBConst.FavoriteTable.Types.TYPE_ALL,
  val sortColumns: Int = DBConst.FavoriteTable.Indexes.TITLE,
  val sortDirection: Int = DBConst.SORT_ASC,
  val searchQuery: String = ""
) {
  fun generateQuery(): SimpleSQLiteQuery {
    val result = StringBuilder().append("SELECT * FROM favorites")
    when (type) {
      DBConst.FavoriteTable.Types.TYPE_MOVIE -> result.append(" WHERE type = 1")
      DBConst.FavoriteTable.Types.TYPE_TV_SHOW -> result.append(" WHERE type = 2")
    }
    if (searchQuery.isNotBlank()) {
      result.append(" AND WHERE item_title LIKE %${searchQuery}%")
    }
    when (sortColumns) {
      DBConst.FavoriteTable.Indexes.TITLE -> result.append("ORDER BY item_title")
      DBConst.FavoriteTable.Indexes.DATE -> result.append("ORDER BY item_date")
      DBConst.FavoriteTable.Indexes.DATE_ADDED -> result.append("ORDER BY date_added")
    }
    when (sortDirection) {
      DBConst.SORT_ASC -> result.append(" ASC")
      DBConst.SORT_DESC -> result.append(" DESC")
    }
    return SimpleSQLiteQuery(result.toString())
  }
}