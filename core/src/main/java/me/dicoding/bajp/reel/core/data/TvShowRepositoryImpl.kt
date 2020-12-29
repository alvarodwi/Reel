package me.dicoding.bajp.reel.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.dicoding.bajp.reel.core.data.db.AppDatabase
import me.dicoding.bajp.reel.core.data.network.ApiService
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.network.json.TvShowJson
import me.dicoding.bajp.reel.core.domain.model.TvShow
import me.dicoding.bajp.reel.core.domain.repository.TvShowRepository
import me.dicoding.bajp.reel.core.utils.API_KEY
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.core.utils.EspressoIdlingResource
import me.dicoding.bajp.reel.core.utils.asDomain
import me.dicoding.bajp.reel.core.utils.asFavoriteEntity
import java.io.IOException

class TvShowRepositoryImpl(
    private val api: ApiService,
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TvShowRepository {
    override fun getPopularTvShow() = flow {
        EspressoIdlingResource.increment()
        try {
            val response = api.getPopularTvShow(API_KEY)
            if (response.isSuccessful) {
                val data = response.body()?.results?.map(TvShowJson::asDomain)
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

    override fun getTvShowDetailData(id: Long) = flow {
        EspressoIdlingResource.increment()
        try {
            val response = api.getTvShowDetail(id, API_KEY)
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

    override suspend fun addTvShowToFavorites(data: TvShow) =
        db.favoriteDao.insertItem(data.asFavoriteEntity())

    override suspend fun removeTvShowFromFavorites(data: TvShow) =
        db.favoriteDao.deleteItem(data.id, Types.TYPE_TV_SHOW)

    override fun isTvShowInFavorites(id: Long) =
        db.favoriteDao.isItemWithIdExists(id, Types.TYPE_TV_SHOW)
}
