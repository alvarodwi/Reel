package me.dicoding.bajp.reel.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.network.json.TvShowJson
import me.dicoding.bajp.reel.core.domain.model.TvShow
import me.dicoding.bajp.reel.core.domain.usecase.TvShowListUseCase
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper.parseStringFromJsonResource
import me.dicoding.bajp.reel.core.utils.asDomain
import me.dicoding.bajp.reel.ui.tvshow.list.TvShowListViewModel
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
  lateinit var useCase: TvShowListUseCase
  private lateinit var viewModel: TvShowListViewModel

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    viewModel = TvShowListViewModel(useCase)
  }

  @Test
  fun `test successful fetch of list tvShow`() {
    every { useCase.getPopularTvShow() } returns flow {
      NetworkResult.Success(
        provideDummyData()
      )
    }
    viewModel.fetchPopularTvShow()

    verify(atLeast = 1) { useCase.getPopularTvShow() }
    confirmVerified(useCase)

    viewModel.tvShows.observeForever { value ->
      assertNotNull(value)
      assertEquals(value.size, 20)
      assertEquals(viewModel.errorMessage.value, "")
    }
  }

  @Test
  fun `test failed fetch of list tvShow`() {
    every { useCase.getPopularTvShow() } returns flow { NetworkResult.Error(Exception("foo")) }
    viewModel.fetchPopularTvShow()

    verify(atLeast = 1) { useCase.getPopularTvShow() }
    confirmVerified(useCase)

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

  private fun provideDummyData(): List<TvShow> {
    return TestFixtureHelper.loadPopularTvShowData(
      parseStringFromJsonResource("/popular_tv_shows.json")
    ).results.map(TvShowJson::asDomain)
  }
}