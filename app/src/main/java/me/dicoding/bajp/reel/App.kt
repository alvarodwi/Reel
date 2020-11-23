package me.dicoding.bajp.reel

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.data.preferences.AppPreferences.nightMode
import me.dicoding.bajp.reel.di.dataModule
import me.dicoding.bajp.reel.di.libModule
import me.dicoding.bajp.reel.di.networkModule
import me.dicoding.bajp.reel.di.viewModelModule
import me.dicoding.bajp.reel.utils.ext.toggleNightMode
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {
  companion object {
    lateinit var app: App
  }

  override fun onCreate() {
    super.onCreate()
    app = this
    // plant timber
    if (BuildConfig.DEBUG) {
      CoroutineScope(Dispatchers.Default).launch {
        Timber.plant(Timber.DebugTree())
      }
    }

    // start injecting di
    startKoin {
      androidLogger(Level.ERROR)
      androidContext(this@App)
      modules(
        listOf(
          viewModelModule, dataModule, networkModule, libModule
        )
      )
    }

    toggleNightMode(nightMode)
  }
}