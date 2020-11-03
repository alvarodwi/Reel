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
import me.dicoding.bajp.reel.data.model.json.TvShowJson
import me.dicoding.bajp.reel.data.model.json.TvShowListJson
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
class TvShowRepositoryTest : TestCase() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var api: ApiService
    lateinit var repository: TvShowRepository
    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = TvShowRepository(api, dispatcher)
    }

    @Test
    fun `test fetchPopularTvShow from local resources`() {
        coEvery { api.getPopularTvShow(API_KEY) } returns Response.success(providePopularTvShow())
        runBlocking { api.getPopularTvShow(API_KEY) }

        coVerify(atLeast = 1) { api.getPopularTvShow(API_KEY) }

        runBlocking {
            repository.getPopularTvShow()
                .collect { result ->
                    assert(result is NetworkResult.Success)
                    result as NetworkResult.Success
                    assertEquals(result.data.size, 20)
                }
        }
    }

    @Test
    fun getTvShowDetailData() {
        coEvery { api.getTvShowDetail(1, API_KEY) } returns Response.success(provideSingleTvShow())
        runBlocking { api.getTvShowDetail(1, API_KEY) }

        coVerify(atLeast = 1) { api.getTvShowDetail(1, API_KEY) }

        runBlocking {
            repository.getTvShowDetailData(1)
                .collect { result ->
                    assert(result is NetworkResult.Success)
                    result as NetworkResult.Success
                    assertEquals(result.data.id, 77169L)
                    assertEquals(result.data.name, "Cobra Kai")
                }
        }
    }

    @After
    fun tearUp() {
        unmockkAll()
    }

    private fun providePopularTvShow(): TvShowListJson {
        return JsonHelper.loadPopularTvShowData(
            TestUtils.parseStringFromJsonResource("/popular_tv_shows.json")
        )
    }

    private fun provideSingleTvShow(): TvShowJson {
        return JsonHelper.loadTvShowData(
            TestUtils.parseStringFromJsonResource("/latest_tv_show.json")
        )
    }
}