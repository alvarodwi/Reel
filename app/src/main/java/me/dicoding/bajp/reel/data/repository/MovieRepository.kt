package me.dicoding.bajp.reel.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import me.dicoding.bajp.reel.data.db.AppDatabase
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.model.entity.asFavoriteEntity
import me.dicoding.bajp.reel.data.model.json.MovieJson
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.ApiService
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.utils.API_KEY
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.utils.EspressoIdlingResource

class MovieRepository(
  private val api: ApiService,
  private val db: AppDatabase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
  fun getPopularMovie(): Flow<NetworkResult<List<MovieEntity>>> = flow {
    EspressoIdlingResource.increment()
    try {
      val response = api.getPopularMovie(API_KEY)
      if (response.isSuccessful) {
        val data = response.body()?.results?.map(MovieJson::asEntity)
          ?: throw(Exception("List is empty"))
        emit(NetworkResult.Success(data))
        EspressoIdlingResource.decrement()
      } else {
        throw(Exception("code : ${response.code()}"))
      }
    } catch (e: Exception) {
      emit(NetworkResult.Error(e))
      EspressoIdlingResource.decrement()
    }
  }.flowOn(dispatcher)

  fun getMovieDetailData(id: Long): Flow<NetworkResult<MovieEntity>> = flow {
    EspressoIdlingResource.increment()
    try {
      val response = api.getMovieDetail(id, API_KEY)
      if (response.isSuccessful) {
        val data = response.body()?.asEntity() ?: throw(Exception("Response body is empty"))
        emit(NetworkResult.Success(data))
        EspressoIdlingResource.decrement()
      } else {
        throw(Exception("code : ${response.code()}"))
      }
    } catch (e: Exception) {
      emit(NetworkResult.Error(e))
      EspressoIdlingResource.decrement()
    }
  }.flowOn(dispatcher)

  suspend fun addMovieToFavorites(data: MovieEntity) {
    withContext(Dispatchers.IO) {
      db.favoriteDao.insertItem(data.asFavoriteEntity())
    }
  }

  suspend fun removeMovieFromFavorites(data: MovieEntity) {
    withContext(Dispatchers.IO) {
      db.favoriteDao.deleteItem(data.id, Types.TYPE_MOVIE)
    }
  }

  fun isMovieInFavorites(id: Long) =
    db.favoriteDao.isItemWithIdExists(id, Types.TYPE_MOVIE)
}