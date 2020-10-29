package me.dicoding.bajp.reel.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.data.model.json.TvShowJson
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.ApiService
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.utils.API_KEY

class TvShowRepository(
    private val api: ApiService
) {
    fun getPopularTvShow(): Flow<NetworkResult<List<TvShowEntity>>> = flow {
        try {
            val response = api.getPopularTvShow(API_KEY)
            if (response.isSuccessful) {
                val data = response.body()?.results?.map(TvShowJson::asEntity)
                    ?: throw(Exception("List is empty"))
                emit(NetworkResult.Success(data))
            } else {
                throw(Exception("code : ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    fun getTvShowDetailData(id: Long): Flow<NetworkResult<TvShowEntity>> = flow {
        try {
            val response = api.getTvShowDetail(id, API_KEY)
            if (response.isSuccessful) {
                val data = response.body()?.asEntity() ?: throw(Exception("Response body is empty"))
                emit(NetworkResult.Success(data))
            } else {
                throw(Exception("code : ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}