package me.dicoding.bajp.reel.data.preferences

import android.content.SharedPreferences
import com.tfcporciuncula.flow.FlowSharedPreferences
import me.dicoding.bajp.reel.App
import org.koin.android.ext.android.inject
import me.dicoding.bajp.reel.data.preferences.PreferencesKey as Keys
import me.dicoding.bajp.reel.utils.DatabaseConstants as DBConst

object AppPreferences {
  private val prefs: SharedPreferences by App.app.inject()
  private val flowPrefs = FlowSharedPreferences(prefs)

  // flowPrefs wrapper
  fun getInt(
    key: String,
    default: Int?
  ) = flowPrefs.getInt(key, default ?: 0)

  fun getString(
    key: String,
    default: String?
  ) = flowPrefs.getString(key, default ?: "")

  fun getStringSet(
    key: String,
    default: Set<String>
  ) = flowPrefs.getStringSet(key, default)

  private fun getBoolean(
    key: String,
    default: Boolean
  ) = flowPrefs.getBoolean(key, default)

  fun nightMode() = getBoolean(Keys.NIGHT_MODE, false)
  fun favItemsSort() = getInt(Keys.FAV_ITEMS_SORT, DBConst.FavoriteTable.Indexes.TITLE)
}