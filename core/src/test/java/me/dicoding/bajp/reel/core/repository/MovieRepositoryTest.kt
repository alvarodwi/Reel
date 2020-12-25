package me.dicoding.bajp.reel.core.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import me.dicoding.bajp.reel.core.data.db.AppDatabase
import me.dicoding.bajp.reel.core.data.model.json.MovieJson
import me.dicoding.bajp.reel.core.data.model.json.MovieListJson
import me.dicoding.bajp.reel.core.data.network.ApiService
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.repository.MovieRepository
import me.dicoding.bajp.reel.core.utils.API_KEY
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper.parseStringFromJsonResource
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

    @MockK
    lateinit var db: AppDatabase
    private lateinit var repository: MovieRepository
    private val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = MovieRepository(api, db, dispatcher)
    }

    @Test
    fun `test fetchPopularMovie from api`() {
        coEvery { api.getPopularMovie(API_KEY) } returns Response.success(providePopularMovie())
        runBlocking { api.getPopularMovie(API_KEY) }

        coVerify(atLeast = 1) { api.getPopularMovie(API_KEY) }
        confirmVerified(api)

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
    fun `test fetchMovieDetail from api`() {
        coEvery { api.getMovieDetail(1, API_KEY) } returns Response.success(provideSingleMovie())
        runBlocking { api.getMovieDetail(1, API_KEY) }

        coVerify(atLeast = 1) { api.getMovieDetail(1, API_KEY) }
        confirmVerified(api)

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

    @Test
    fun `test checkMovieExists from db`() {
        coEvery { db.favoriteDao.isItemWithIdExists(1, Types.TYPE_MOVIE) } returns flow {
            emit(1)
        }
        runBlocking { db.favoriteDao.isItemWithIdExists(1, Types.TYPE_MOVIE) }

        coVerify(atLeast = 1) { db.favoriteDao.isItemWithIdExists(1, Types.TYPE_MOVIE) }
        confirmVerified(db)

        runBlocking {
            repository.isMovieInFavorites(1)
                .collect { result ->
                    assertEquals(result, 1)
                }
        }
    }

    @After
    fun tearUp() {
        unmockkAll()
    }

    private fun providePopularMovie(): MovieListJson {
        return TestFixtureHelper.loadPopularMovieData(
            parseStringFromJsonResource("/popular_movies.json")
        )
    }

    private fun provideSingleMovie(): MovieJson {
        return TestFixtureHelper.loadMovieData(
            parseStringFromJsonResource("/latest_movie.json")
        )
    }
}