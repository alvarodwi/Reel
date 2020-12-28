package me.dicoding.bajp.reel.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import me.dicoding.bajp.reel.App
import org.koin.android.ext.android.inject
import me.dicoding.bajp.reel.prefs.PreferencesKey as Keys

object AppPreferences {
  private val prefs: SharedPreferences by App.app.inject()

  var nightMode
    get() = prefs.getBoolean(Keys.NIGHT_MODE, false)
    set(value) = prefs.edit { putBoolean(Keys.NIGHT_MODE, value) }
}