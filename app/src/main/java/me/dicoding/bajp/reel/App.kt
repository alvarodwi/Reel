package me.dicoding.bajp.reel

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.core.di.databaseModule
import me.dicoding.bajp.reel.core.di.libModule
import me.dicoding.bajp.reel.core.di.networkModule
import me.dicoding.bajp.reel.core.di.repositoryModule
import me.dicoding.bajp.reel.di.viewModelModule
import me.dicoding.bajp.reel.ext.toggleNightMode
import me.dicoding.bajp.reel.prefs.AppPreferences.nightMode
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
          databaseModule, networkModule, repositoryModule, viewModelModule, libModule
        )
      )
    }

    toggleNightMode(nightMode)
  }
}