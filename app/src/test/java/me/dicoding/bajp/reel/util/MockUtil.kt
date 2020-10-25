package me.dicoding.bajp.reel.util

import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity

object MockUtil {

    fun mockMovie() = MovieEntity(
        id = 528085,
        title = "2067",
        overview = "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
        tagLine = "The fight for the future has begun.",
        releaseDate = "2020-10-01",
        poster = "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
        backdrop = "/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg",
        genres = listOf("Science Fiction","Thriller","Drama"),
        homepage = "http://screen.nsw.gov.au/project/2067"
        )

    fun mockTvShow() = TvShowEntity(
        id = 77169,
        name = "Cobra Kai",
        overview = "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
        firstAirDate = "2018-05-02",
        poster = "/eTMMU2rKpZRbo9ERytyhwatwAjz.jpg",
        backdrop = "/g63KmFgqkvXu6WKS23V56hqEidh.jpg",
        genres = listOf("Action & Adventure","Drama"),
        homepage = "https://www.netflix.com/title/81002370"
    )

    fun mockMovieList() = listOf(mockMovie())
    fun mockTvShowList() = listOf(mockTvShow())
}