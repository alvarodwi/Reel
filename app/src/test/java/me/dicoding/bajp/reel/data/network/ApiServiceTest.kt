package me.dicoding.bajp.reel.data.network

import kotlinx.coroutines.runBlocking
import me.dicoding.bajp.reel.utils.API_KEY
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ApiServiceTest : ApiAbstract<ApiService>(){
    private lateinit var service : ApiService

    @Before
    fun init(){
        service = createService(ApiService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun getPopularMovie() = runBlocking {
        enqueueResponse("/popular_movies.json")
        val response = service.getPopularMovie(API_KEY)
        mockWebServer.takeRequest()

        assertNotNull(response.body())
        assertEquals(response.body()?.page,1)
        assertEquals(response.body()?.results?.size,20)
        assertEquals(response.body()?.totalPages,500)
        assertEquals(response.body()?.totalResults,10000)

        assertEquals(response.body()?.results?.get(0)?.title,"2067")
    }

    @Throws(IOException::class)
    @Test
    fun getPopularTvShow() = runBlocking {
        enqueueResponse("/popular_tv_shows.json")
        val response = service.getPopularTvShow(API_KEY)
        mockWebServer.takeRequest()

        assertNotNull(response.body())
        assertEquals(response.body()?.page,1)
        assertEquals(response.body()?.results?.size,20)
        assertEquals(response.body()?.totalPages,500)
        assertEquals(response.body()?.totalResults,10000)

        assertEquals(response.body()?.results?.get(0)?.name,"Cobra Kai")
    }

    @Throws(IOException::class)
    @Test
    fun getMovieDetail() = runBlocking {
        enqueueResponse("/latest_movie.json")
        val response = service.getMovieDetail( 528085,API_KEY)
        mockWebServer.takeRequest()

        assertNotNull(response.body())
        assertEquals(response.body()?.id, 528085L)
        assertEquals(response.body()?.title,"2067")
    }

    @Throws(IOException::class)
    @Test
    fun getTvShowDetail() = runBlocking {
        enqueueResponse("/latest_tv_show.json")
        val response = service.getTvShowDetail( 77169,API_KEY)
        mockWebServer.takeRequest()

        assertNotNull(response.body())
        assertEquals(response.body()?.id, 77169L)
        assertEquals(response.body()?.name,"Cobra Kai")
    }
}