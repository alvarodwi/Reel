package me.dicoding.bajp.reel.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import me.dicoding.bajp.reel.data.model.json.MovieJson
import me.dicoding.bajp.reel.data.model.json.MovieListJson
import me.dicoding.bajp.reel.data.network.ApiService
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.utils.API_KEY
import me.dicoding.bajp.reel.utils.JsonHelper
import me.dicoding.bajp.reel.utils.TestUtils
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class MovieRepositoryTest : TestCase() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var api: ApiService
    lateinit var repository: MovieRepository
    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = MovieRepository(api, dispatcher)
    }

    @Test
    fun `test fetchPopularMovie from local resources`() {
        coEvery { api.getPopularMovie(API_KEY) } returns Response.success(providePopularMovie())
        runBlocking { api.getPopularMovie(API_KEY) }

        coVerify(atLeast = 1) { api.getPopularMovie(API_KEY) }

        runBlocking {
            repository.getPopularMovie()
                .collect { result ->
                    assert(result is NetworkResult.Success)
                    result as NetworkResult.Success
                    assertEquals(result.data.size, 20)
                }
        }
    }

    @Test
    fun getMovieDetailData() {
        coEvery { api.getMovieDetail(1, API_KEY) } returns Response.success(provideSingleMovie())
        runBlocking { api.getMovieDetail(1, API_KEY) }

        coVerify(atLeast = 1) { api.getMovieDetail(1, API_KEY) }

        runBlocking {
            repository.getMovieDetailData(1)
                .collect { result ->
                    assert(result is NetworkResult.Success)
                    result as NetworkResult.Success
                    assertEquals(result.data.id, 528085L)
                    assertEquals(result.data.title, "2067")
                }
        }
    }

    @After
    fun tearUp() {
        unmockkAll()
    }

    private fun providePopularMovie(): MovieListJson {
        return JsonHelper.loadPopularMovieData(
            TestUtils.parseStringFromJsonResource("/popular_movies.json")
        )
    }

    private fun provideSingleMovie(): MovieJson {
        return JsonHelper.loadMovieData(
            TestUtils.parseStringFromJsonResource("/latest_movie.json")
        )
    }
}