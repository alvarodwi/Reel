package me.dicoding.bajp.reel.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import coil.ImageLoader
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.dicoding.bajp.reel.core.BuildConfig
import me.dicoding.bajp.reel.core.data.FavoriteRepositoryImpl
import me.dicoding.bajp.reel.core.data.MovieRepositoryImpl
import me.dicoding.bajp.reel.core.data.TvShowRepositoryImpl
import me.dicoding.bajp.reel.core.data.db.AppDatabase
import me.dicoding.bajp.reel.core.data.network.ApiService
import me.dicoding.bajp.reel.core.domain.repository.FavoriteRepository
import me.dicoding.bajp.reel.core.domain.repository.MovieRepository
import me.dicoding.bajp.reel.core.domain.repository.TvShowRepository
import me.dicoding.bajp.reel.core.utils.BASE_URL
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber

val databaseModule = module {
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "db_reel")
            .fallbackToDestructiveMigration()
            .setQueryExecutor(Dispatchers.IO.asExecutor())
            .setTransactionExecutor(Dispatchers.IO.asExecutor())
            .build()

    single { provideAppDatabase(androidContext()) }
}

@Suppress("MagicNumber")
@OptIn(ExperimentalSerializationApi::class)
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
            val logger = HttpLoggingInterceptor { message ->
                Timber.d("API: $message")
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

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<TvShowRepository> { TvShowRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
}

val libModule = module {
    // coil
    fun provideCoilLoader(
        app: Application,
        client: OkHttpClient
    ) = ImageLoader.Builder(app)
        .okHttpClient(client)
        .crossfade(true)
        .build()

    single { provideCoilLoader(androidApplication(), get()) }

    // pref
    fun provideUserPreferences(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)

    single { provideUserPreferences(androidApplication()) }
}
