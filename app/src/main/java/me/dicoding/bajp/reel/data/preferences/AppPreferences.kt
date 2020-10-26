package me.dicoding.bajp.reel.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import me.dicoding.bajp.reel.App
import org.koin.android.ext.android.inject

object AppPreferences {
    private val prefs: SharedPreferences by App.app.inject()

    var nightMode
        get() = prefs.getBoolean("night_mode", false)
        set(value) = prefs.edit { putBoolean("night_mode", value) }
}