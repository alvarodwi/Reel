package me.dicoding.bajp.reel.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.dicoding.bajp.reel.core.data.db.AppDatabase
import me.dicoding.bajp.reel.core.data.network.ApiService
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.network.json.MovieJson
import me.dicoding.bajp.reel.core.domain.model.Movie
import me.dicoding.bajp.reel.core.domain.repository.MovieRepository
import me.dicoding.bajp.reel.core.utils.API_KEY
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.core.utils.EspressoIdlingResource
import me.dicoding.bajp.reel.core.utils.asDomain
import me.dicoding.bajp.reel.core.utils.asFavoriteEntity
import java.io.IOException

class MovieRepositoryImpl(
    private val api: ApiService,
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {
    override fun getPopularMovie() = flow {
        EspressoIdlingResource.increment()
        try {
            val response = api.getPopularMovie(API_KEY)
            if (response.isSuccessful) {
                val data = response.body()?.results?.map(MovieJson::asDomain)
                    ?: throw Exception("List is empty")
                emit(NetworkResult.Success(data))
                EspressoIdlingResource.decrement()
            } else {
                throw Exception("code : ${response.code()}")
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error(e))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(dispatcher)

    override fun getMovieDetailData(id: Long) = flow {
        EspressoIdlingResource.increment()
        try {
            val response = api.getMovieDetail(id, API_KEY)
            if (response.isSuccessful) {
                val data = response.body()?.asDomain() ?: throw Exception("Response body is empty")
                emit(NetworkResult.Success(data))
                EspressoIdlingResource.decrement()
            } else {
                throw Exception("code : ${response.code()}")
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error(e))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(dispatcher)

    override suspend fun addMovieToFavorites(data: Movie) =
        db.favoriteDao.insertItem(data.asFavoriteEntity())

    override suspend fun removeMovieFromFavorites(data: Movie) =
        db.favoriteDao.deleteItem(data.id, Types.TYPE_MOVIE)

    override fun isMovieInFavorites(id: Long) =
        db.favoriteDao.isItemWithIdExists(id, Types.TYPE_MOVIE)
}
