package me.dicoding.bajp.reel.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.utils.ext.toggleNightMode

//this class is used on fragment_settings.xml!
class MainSettingsScreen : PreferenceFragmentCompat() {

  override fun onCreatePreferences(
    savedInstanceState: Bundle?,
    rootKey: String?
  ) {
    setPreferencesFromResource(R.xml.root_preferences, rootKey)

    findPreference<SwitchPreferenceCompat>("night_mode")?.let { pref ->
      pref.setDefaultValue(false)
      pref.setOnPreferenceChangeListener { _, newValue ->
        toggleNightMode(newValue as Boolean)
        activity?.recreate()
        true
      }
    }
  }
}