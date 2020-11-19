package me.dicoding.bajp.reel.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.data.model.json.TvShowJson
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.TvShowRepository
import me.dicoding.bajp.reel.ui.tvshow.list.TvShowListViewModel
import me.dicoding.bajp.reel.utils.JsonHelper
import me.dicoding.bajp.reel.utils.TestUtils
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvShowListViewModelTest : TestCase() {
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @MockK
  lateinit var repository: TvShowRepository
  private lateinit var viewModel: TvShowListViewModel

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    viewModel = TvShowListViewModel(repository)
  }

  @Test
  fun `test successful fetch of list tvShow`() {
    every { repository.getPopularTvShow() } returns flow {
      NetworkResult.Success(
        provideDummyData()
      )
    }
    viewModel.fetchPopularTvShow()

    verify(atLeast = 1) { repository.getPopularTvShow() }
    confirmVerified(repository)

    viewModel.tvShows.observeForever { value ->
      assertNotNull(value)
      assertEquals(value.size, 20)
      assertEquals(viewModel.errorMessage.value, "")
    }
  }

  @Test
  fun `test failed fetch of list tvShow`() {
    every { repository.getPopularTvShow() } returns flow { NetworkResult.Error(Exception("foo")) }
    viewModel.fetchPopularTvShow()

    verify(atLeast = 1) { repository.getPopularTvShow() }
    confirmVerified(repository)

    viewModel.tvShows.observeForever { value ->
      assertNotNull(value)
      assert(value.isEmpty())
      assertEquals(viewModel.errorMessage.value, "foo")
    }
  }

  @After
  fun tearUp() {
    unmockkAll()
  }

  private fun provideDummyData(): List<TvShowEntity> {
    return JsonHelper.loadPopularTvShowData(
      TestUtils.parseStringFromJsonResource("/popular_tv_shows.json")
    ).results.map(TvShowJson::asEntity)
  }
}