package me.dicoding.bajp.reel.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import me.dicoding.bajp.reel.App
import me.dicoding.bajp.reel.data.preferences.PreferencesKey.NIGHT_MODE
import org.koin.android.ext.android.inject

object AppPreferences {
  private val prefs: SharedPreferences by App.app.inject()

  var nightMode
    get() = prefs.getBoolean(NIGHT_MODE, false)
    set(value) = prefs.edit { putBoolean(NIGHT_MODE, value) }
}