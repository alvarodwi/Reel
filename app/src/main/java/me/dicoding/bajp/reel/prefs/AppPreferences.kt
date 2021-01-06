package me.dicoding.bajp.reel.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import me.dicoding.bajp.reel.App
import me.dicoding.bajp.reel.prefs.PreferencesKey as Keys
import org.koin.android.ext.android.inject

object AppPreferences {
    private val prefs: SharedPreferences by App.instance.inject()

    var nightMode
        get() = prefs.getBoolean(Keys.NIGHT_MODE, false)
        set(value) = prefs.edit { putBoolean(Keys.NIGHT_MODE, value) }
}
