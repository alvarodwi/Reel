package me.dicoding.bajp.reel.data.model.query

import androidx.sqlite.db.SimpleSQLiteQuery
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Indexes
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.utils.DatabaseConstants.SORT_ASC
import me.dicoding.bajp.reel.utils.DatabaseConstants.SORT_DESC

data class FavoriteQuery(
  var type: Int = Types.TYPE_ALL,
  var sortColumns: Int = Indexes.TITLE,
  var sortDirection: Int = SORT_ASC,
  var searchQuery: String = ""
) {
  fun generateQuery(): SimpleSQLiteQuery {
    val result = StringBuilder().append("SELECT * FROM favorites")
    result.append(" WHERE item_title LIKE '%${searchQuery}%'")
    if (type != Types.TYPE_ALL) result.append(" AND")
    when (type) {
      Types.TYPE_MOVIE -> result.append(" type = 1")
      Types.TYPE_TV_SHOW -> result.append(" type = 2")
    }

    when (sortColumns) {
      Indexes.TITLE -> result.append(" ORDER BY item_title")
      Indexes.DATE -> result.append(" ORDER BY item_date")
      Indexes.DATE_ADDED -> result.append(" ORDER BY date_added")
    }
    when (sortDirection) {
      SORT_ASC -> result.append(" ASC")
      SORT_DESC -> result.append(" DESC")
    }
    return SimpleSQLiteQuery(result.toString())
  }
}