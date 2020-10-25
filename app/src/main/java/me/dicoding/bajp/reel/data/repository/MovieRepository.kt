package me.dicoding.bajp.reel.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.model.json.MovieJson
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.ApiService
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.utils.API_KEY

class MovieRepository(private val api: ApiService) {
    fun getPopularMovie(): Flow<NetworkResult<List<MovieEntity>>> = flow {
        try {
            val response = api.getPopularMovie(API_KEY)
            if (response.isSuccessful) {
                val data = response.body()?.results?.map(MovieJson::asEntity) ?: throw(Exception("List is empty"))
                emit(NetworkResult.Success(data))
            } else {
                throw(Exception("code : ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getMovieDetailData(id: Long): Flow<NetworkResult<MovieEntity>> = flow {
        try {
            val response = api.getMovieDetail(id, API_KEY)
            if (response.isSuccessful) {
                val data = response.body()?.asEntity() ?: throw(Exception("Response body is empty"))
                emit(NetworkResult.Success(data))
            } else {
                throw(Exception("code : ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}