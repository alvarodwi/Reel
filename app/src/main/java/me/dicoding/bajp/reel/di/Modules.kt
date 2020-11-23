package me.dicoding.bajp.reel.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import coil.ImageLoader
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.serialization.json.Json
import me.dicoding.bajp.reel.BuildConfig
import me.dicoding.bajp.reel.data.db.AppDatabase
import me.dicoding.bajp.reel.data.network.ApiService
import me.dicoding.bajp.reel.data.repository.FavoriteRepository
import me.dicoding.bajp.reel.data.repository.MovieRepository
import me.dicoding.bajp.reel.data.repository.TvShowRepository
import me.dicoding.bajp.reel.ui.favorite.FavoriteViewModel
import me.dicoding.bajp.reel.ui.movie.detail.MovieDetailViewModel
import me.dicoding.bajp.reel.ui.movie.list.MovieListViewModel
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailViewModel
import me.dicoding.bajp.reel.ui.tvshow.list.TvShowListViewModel
import me.dicoding.bajp.reel.utils.BASE_URL
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

val viewModelModule = module {
  viewModel { MovieListViewModel(get()) }
  viewModel { (id: Long) -> MovieDetailViewModel(id, get()) }

  viewModel { TvShowListViewModel(get()) }
  viewModel { (id: Long) -> TvShowDetailViewModel(id, get()) }

  viewModel { FavoriteViewModel(get()) }
}

val dataModule = module {
  //db
  fun provideAppDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "db_reel")
      .fallbackToDestructiveMigration()
      .setQueryExecutor(Dispatchers.IO.asExecutor())
      .setTransactionExecutor(Dispatchers.IO.asExecutor())
      .build()

  single { provideAppDatabase(androidContext()) }

  //repository
  single { MovieRepository(get(), get()) }
  single { TvShowRepository(get(), get()) }
  single { FavoriteRepository(get()) }
}

val networkModule = module {
  fun provideNetworkCache(application: Application): Cache {
    val cacheSize: Long = 10 * 1024 * 1024
    return Cache(application.cacheDir, cacheSize)
  }

  fun provideHttpClient(cache: Cache): OkHttpClient {
    val builder = OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(10, TimeUnit.SECONDS)
      .writeTimeout(10, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
      val logger = HttpLoggingInterceptor { messsage ->
        Timber.d("API: $messsage")
      }.apply {
        level = HttpLoggingInterceptor.Level.BASIC
      }
      builder.addInterceptor(logger)
    }

    return builder
      .cache(cache)
      .build()
  }

  fun provideRetrofit(client: OkHttpClient): Retrofit {
    val contentType = "application/json".toMediaType()
    val jsonConverterFactory = Json {
      ignoreUnknownKeys = true
      isLenient = true
    }.asConverterFactory(contentType)

    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(jsonConverterFactory)
      .build()
  }

  single { provideNetworkCache(androidApplication()) }
  single { provideHttpClient(get()) }
  single { provideRetrofit(get()) }

  fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

  single { provideApiService(get()) }
}

val libModule = module {
  //coil
  fun provideCoilLoader(
    app: Application,
    client: OkHttpClient
  ) = ImageLoader.Builder(app)
    .availableMemoryPercentage(0.25)
    .okHttpClient(client)
    .crossfade(true)
    .build()

  single { provideCoilLoader(androidApplication(), get()) }

  //pref
  fun provideUserPreferences(app: Application): SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(app)

  single { provideUserPreferences(androidApplication()) }
}