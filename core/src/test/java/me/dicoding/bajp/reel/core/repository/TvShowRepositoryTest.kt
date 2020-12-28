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
import me.dicoding.bajp.reel.core.data.TvShowRepositoryImpl
import me.dicoding.bajp.reel.core.data.db.AppDatabase
import me.dicoding.bajp.reel.core.data.network.ApiService
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.network.json.TvShowJson
import me.dicoding.bajp.reel.core.data.network.json.TvShowListJson
import me.dicoding.bajp.reel.core.domain.repository.TvShowRepository
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
class TvShowRepositoryTest : TestCase() {
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @MockK
  lateinit var api: ApiService

  @MockK
  lateinit var db: AppDatabase
  private lateinit var repository: TvShowRepository
  private val dispatcher = Dispatchers.Unconfined

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    repository = TvShowRepositoryImpl(api, db, dispatcher)
  }

  @Test
  fun `test fetchPopularTvShow from local resources`() {
    coEvery {
      api.getPopularTvShow(API_KEY)
    } returns Response.success(providePopularTvShow())
    runBlocking { api.getPopularTvShow(API_KEY) }

    coVerify(atLeast = 1) { api.getPopularTvShow(API_KEY) }
    confirmVerified(api)

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
    confirmVerified(api)

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

  @Test
  fun `test checkTvShowExists from db`() {
    coEvery { db.favoriteDao.isItemWithIdExists(1, Types.TYPE_TV_SHOW) } returns flow {
      emit(1)
    }
    runBlocking { db.favoriteDao.isItemWithIdExists(1, Types.TYPE_TV_SHOW) }

    coVerify(atLeast = 1) { db.favoriteDao.isItemWithIdExists(1, Types.TYPE_TV_SHOW) }
    confirmVerified(db)

    runBlocking {
      repository.isTvShowInFavorites(1)
        .collect { result ->
          assertEquals(result, 1)
        }
    }
  }

  @After
  fun tearUp() {
    unmockkAll()
  }

  private fun providePopularTvShow(): TvShowListJson {
    return TestFixtureHelper.loadPopularTvShowData(
      parseStringFromJsonResource("/popular_tv_shows.json")
    )
  }

  private fun provideSingleTvShow(): TvShowJson {
    return TestFixtureHelper.loadTvShowData(
      parseStringFromJsonResource("/latest_tv_show.json")
    )
  }
}