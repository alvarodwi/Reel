package me.dicoding.bajp.reel.core.data.network

import me.dicoding.bajp.reel.core.data.network.json.MovieJson
import me.dicoding.bajp.reel.core.data.network.json.MovieListJson
import me.dicoding.bajp.reel.core.data.network.json.TvShowJson
import me.dicoding.bajp.reel.core.data.network.json.TvShowListJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // movies
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
    ): Response<MovieJson>

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
    ): Response<MovieListJson>

    // tv shows
    @GET("tv/{tv_show_id}")
    suspend fun getTvShowDetail(
        @Path("tv_show_id") tvShowId: Long,
        @Query("api_key") apiKey: String,
    ): Response<TvShowJson>

    @GET("tv/popular")
    suspend fun getPopularTvShow(
        @Query("api_key") apiKey: String,
    ): Response<TvShowListJson>
}
