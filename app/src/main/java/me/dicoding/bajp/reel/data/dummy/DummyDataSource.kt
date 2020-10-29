package me.dicoding.bajp.reel.data.dummy

import me.dicoding.bajp.reel.utils.JsonHelper

class DummyDataSource(private val jsonHelper : JsonHelper){
    fun getMovie() = jsonHelper.loadMovieData()

    fun getTvShow() = jsonHelper.loadTvShowData()

    fun getPopularMovie() = jsonHelper.loadPopularMovieData()

    fun getPopularTvShow() = jsonHelper.loadPopularTvShowData()
}