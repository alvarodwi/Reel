package me.dicoding.bajp.reel.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.dicoding.bajp.reel.data.db.AppDatabase
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.data.model.entity.asFavoriteEntity
import me.dicoding.bajp.reel.data.model.json.TvShowJson
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.ApiService
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.utils.API_KEY
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.utils.EspressoIdlingResource

class TvShowRepository(
  private val api: ApiService,
  private val db: AppDatabase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
  fun getPopularTvShow(): Flow<NetworkResult<List<TvShowEntity>>> = flow {
    EspressoIdlingResource.increment()
    try {
      val response = api.getPopularTvShow(API_KEY)
      if (response.isSuccessful) {
        val data = response.body()?.results?.map(TvShowJson::asEntity)
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

  fun getTvShowDetailData(id: Long): Flow<NetworkResult<TvShowEntity>> = flow {
    EspressoIdlingResource.increment()
    try {
      val response = api.getTvShowDetail(id, API_KEY)
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

  suspend fun addTvShowToFavorites(data: TvShowEntity) =
    db.favoriteDao.insertItem(data.asFavoriteEntity())

  suspend fun removeTvShowFromFavorites(data: TvShowEntity) =
    db.favoriteDao.deleteItem(data.id, Types.TYPE_TV_SHOW)

  fun isTvShowInFavorites(id: Long) =
    db.favoriteDao.isItemWithIdExists(id, Types.TYPE_TV_SHOW)
}