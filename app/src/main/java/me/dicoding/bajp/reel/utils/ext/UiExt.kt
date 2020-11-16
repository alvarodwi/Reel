package me.dicoding.bajp.reel.utils.ext

import androidx.appcompat.app.AppCompatDelegate

// night mode helper
fun toggleNightMode(value: Boolean) {
  AppCompatDelegate.setDefaultNightMode(
    if (value) AppCompatDelegate.MODE_NIGHT_YES
    else AppCompatDelegate.MODE_NIGHT_NO
  )
}