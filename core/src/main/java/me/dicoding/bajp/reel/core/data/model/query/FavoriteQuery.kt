package me.dicoding.bajp.reel.core.data.model.query

import androidx.sqlite.db.SimpleSQLiteQuery
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Sorts
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types

data class FavoriteQuery(
  var type: Int = Types.TYPE_ALL,
  var sort: Int = Sorts.TITLE_ASC,
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

    when (sort) {
      Sorts.TITLE_ASC -> result.append(" ORDER BY item_title ASC")
      Sorts.TITLE_DESC -> result.append(" ORDER BY item_title DESC")
      Sorts.DATE_ASC -> result.append(" ORDER BY item_date ASC")
      Sorts.DATE_DESC -> result.append(" ORDER BY item_date DESC")
      Sorts.DATE_ADDED_ASC -> result.append(" ORDER BY date_added ASC")
      Sorts.DATE_ADDED_DESC -> result.append(" ORDER BY date_added DESC")
    }
    return SimpleSQLiteQuery(result.toString())
  }
}