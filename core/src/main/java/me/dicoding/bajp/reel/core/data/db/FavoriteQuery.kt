package me.dicoding.bajp.reel.core.data.db

import androidx.sqlite.db.SimpleSQLiteQuery
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Sorts
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types

data class FavoriteQuery(
    var type: Int = Types.TYPE_ALL,
    var sort: Int = Sorts.TITLE_ASC,
    var searchQuery: String = ""
) {
    fun generateQuery(isValidating: Boolean = false): SimpleSQLiteQuery {
        val result = StringBuilder().apply {
            generateFilterBaseQuery(isValidating)
            generateFilterTypeQuery()
            generateFilterSortQuery()
        }
        return SimpleSQLiteQuery(result.toString())
    }

    private val defaultQuery = "SELECT * FROM favorites"
    private val validatingQuery =
        "SELECT COUNT(*) FROM favorites"

    private fun StringBuilder.generateFilterBaseQuery(flag: Boolean) {
        if (flag) append(validatingQuery) else append(defaultQuery)
        append(" WHERE item_title LIKE '%$searchQuery%'")
    }

    private fun StringBuilder.generateFilterTypeQuery() {
        if (type != Types.TYPE_ALL) append(" AND")
        when (type) {
            Types.TYPE_MOVIE -> append(" type = 1")
            Types.TYPE_TV_SHOW -> append(" type = 2")
        }
    }

    private fun StringBuilder.generateFilterSortQuery() {
        when (sort) {
            Sorts.TITLE_ASC -> append(" ORDER BY item_title ASC")
            Sorts.TITLE_DESC -> append(" ORDER BY item_title DESC")
            Sorts.DATE_ASC -> append(" ORDER BY item_date ASC")
            Sorts.DATE_DESC -> append(" ORDER BY item_date DESC")
            Sorts.DATE_ADDED_ASC -> append(" ORDER BY date_added ASC")
            Sorts.DATE_ADDED_DESC -> append(" ORDER BY date_added DESC")
            else -> throw IllegalArgumentException("Unknown sorting type")
        }
    }
}
